package com.app.domain.interactor.transaction.make

import com.app.domain.exception.Error
import com.app.domain.model.Account
import com.app.domain.model.Transaction

/**
 * Interface for making financial transactions associated with a specific account.
 *
 * This interface defines a contract for making financial transactions.
 * Implementing classes are responsible for handling deposit and withdrawal operations.
 *
 * @see Transaction
 */
interface MakeTransaction {
    /**
     * Perform a deposit operation on the specified account.
     *
     * @param account The target account for the deposit.
     * @param amount The amount to be deposited. Must be a positive value.
     * @return The [Account] object updated after performing the transaction.
     * @throws [Error.TransactionError.NonPositiveAmountError] if the deposit fails due to a non-positive amount.
     */
    @Throws(Error.TransactionError.NonPositiveAmountError::class)
    suspend fun deposit(account: Account, amount: Double): Account

    /**
     * Perform a withdrawal operation on the specified account.
     *
     * @param account The target account for the withdrawal.
     * @param amount The amount to be withdrawn. Must be a positive value.
     * @return The [Account] object updated after performing the transaction.
     * @throws [Error.TransactionError] if the withdrawal fails due to insufficient funds or other errors.
     */
    @Throws(Error.TransactionError::class)
    suspend fun withdrawal(account: Account, amount: Double): Account
}
