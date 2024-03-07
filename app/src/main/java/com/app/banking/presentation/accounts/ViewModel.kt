package com.app.banking.presentation.accounts

import androidx.lifecycle.viewModelScope
import com.app.banking.presentation.ErrorConverter
import com.app.domain.interactor.account.search.SearchAccounts
import com.app.domain.interactor.transaction.make.MakeTransaction
import com.app.domain.model.Account
import com.app.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

/**
 * ViewModel for managing the UI state and logic related to accounts.
 *
 * @param searchAccounts The use case responsible for searching user's accounts.
 * @param makeTransaction The use case responsible for making financial transactions.
 */
@HiltViewModel
class ViewModel @Inject constructor(
    private val errorConverter: ErrorConverter,
    private val searchAccounts: SearchAccounts,
    private val makeTransaction: MakeTransaction
) : androidx.lifecycle.ViewModel() {

    @TestOnly
    constructor(searchAccounts: SearchAccounts, makeTransaction: MakeTransaction):
            this(ErrorConverter(), searchAccounts, makeTransaction)

    private val _uiState = MutableStateFlow(UiState())

    // Exposes the UI state as a read-only flow to observe changes
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /**
     * Handles the given [intent] to trigger corresponding actions.
     *
     * @param intent The view intent representing user actions or events.
     *
     * @see ViewIntent
     */
    fun handle(intent: ViewIntent) = when(intent) {
        is ViewIntent.TransactionFormIntent.Show -> update(uiState.value.copy(transactionType = intent.type))
        is ViewIntent.TransactionFormIntent.Hide -> update(uiState.value.copy(transactionType = null))
        is ViewIntent.LoadAccounts -> loadAccounts(intent.user)
        is ViewIntent.Deposit -> performTransaction { makeTransaction.deposit(intent.account, intent.amount) }
        is ViewIntent.Withdrawal -> performTransaction { makeTransaction.withdrawal(intent.account, intent.amount) }
    }

    private fun loadAccounts(user: User, state: UiState = uiState.value) {
        update(state.copy(loadState = LoadState.Loading))
        viewModelScope.launch {
            try {
                val accounts = searchAccounts(user)
                update(state.copy(loadState = LoadState.Success(accounts)))
            } catch (e: Exception) {
                val message = errorConverter.convert(e)
                update(state.copy(loadState = LoadState.Failure(message = message)))
            }
        }
    }

    private fun performTransaction(state: UiState = uiState.value, perform: suspend () -> Account) {
        update(state.copy(transactionState = TransactionState.Loading))
        viewModelScope.launch {
            try {
                val account: Account = perform() // Updated account after performing transaction
                val accounts = state.accounts.map { if (it.id == account.id) account else it }
                val loadState = LoadState.Success(accounts = accounts)
                val transactionState = TransactionState.Success
                update(state.copy(loadState = loadState, transactionState = transactionState, transactionType = null))
            } catch (e: Exception) {
                val message = errorConverter.convert(e)
                update(state.copy(transactionState = TransactionState.Failure(message = message)))
            }
        }
    }

    @TestOnly
    fun update(uiState: UiState) {
        _uiState.value = uiState
    }
}
