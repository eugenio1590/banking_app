package com.app.domain.interactor.transaction.make

import com.app.domain.model.Account
import com.app.domain.model.Transaction
import com.app.domain.repository.AccountRepository
import com.app.domain.repository.TransactionRepository
import com.app.domain.exception.Error.TransactionError

/**
 * Use case for making financial transactions.
 *
 * This internal class implements the [MakeTransaction] interface, providing logic
 * for processing transactions and maintaining the account information.
 *
 * @property transactionRepository The repository responsible for managing transactions.
 * @property accountRepository The repository responsible for managing accounts.
 */
internal class MakeTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : MakeTransaction {
    override suspend fun deposit(account: Account, amount: Double): Account {
        if (amount <= 0) throw TransactionError.NonPositiveAmountError
        val balance = account.balance + amount
        val transaction = Transaction(Transaction.Type.DEPOSIT, amount, account)
        perform(transaction, account.copy(balance = balance))
        return account.copy(balance = balance)
    }

    override suspend fun withdrawal(account: Account, amount: Double): Account {
        if (amount <= 0) throw TransactionError.NonPositiveAmountError
        val balance = account.balance - amount
        return when {
            (account.balance == 0.0) or (balance == 0.0) -> throw TransactionError.ZeroBalanceError
            balance < 0f -> throw TransactionError.InsufficientBalanceError
            else -> {
                val transaction = Transaction(Transaction.Type.WITHDRAWAL, amount, account)
                perform(transaction, account.copy(balance = balance))
                account.copy(balance = balance)
            }
        }
    }

    private suspend fun perform(transaction: Transaction, account: Account) {
        var result = transactionRepository.add(transaction)
        result = result and accountRepository.update(account)
        if (!result) throw TransactionError.TransactionProcessingError
    }
}
