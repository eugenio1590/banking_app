package com.app.banking.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.banking.R
import com.app.banking.presentation.accounts.LoadState
import com.app.banking.presentation.accounts.ViewIntent
import com.app.banking.presentation.accounts.ViewIntent.TransactionFormIntent
import com.app.banking.presentation.accounts.ViewModel
import com.app.banking.ui.account.search.AccountList
import com.app.banking.ui.component.FloatingButton
import com.app.banking.ui.transaction.form.TransactionForm
import com.app.domain.model.Transaction
import com.app.domain.model.User

@Composable
fun AccountsScreen(user: User, viewModel: ViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val loadState = uiState.loadState
    Column {
        when (loadState) {
            is LoadState.Initial -> Spacer(modifier = Modifier).also {
                viewModel.handle(ViewIntent.LoadAccounts(user))
            }
            is LoadState.Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            is LoadState.Success -> Scaffold(
                content = { Box(modifier = Modifier.padding(it)) {
                    val transactionType = uiState.transactionType
                    if (transactionType != null) {
                        TransactionForm(
                            type = transactionType,
                            accounts = loadState.accounts,
                            onPerform = { amount, account ->
                                val intent = when (transactionType) {
                                    Transaction.Type.DEPOSIT -> ViewIntent.Deposit(account, amount)
                                    Transaction.Type.WITHDRAWAL -> ViewIntent.Withdrawal(account, amount)
                                }
                                viewModel.handle(intent)
                            },
                            onCancel = {
                                viewModel.handle(TransactionFormIntent.Hide)
                            })
                    }
                    AccountList(accounts = loadState.accounts)
                } },
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        FloatingButton(
                            enabled = uiState.isDepositAvailable,
                            onClick = {
                                val intent = TransactionFormIntent.Show(Transaction.Type.DEPOSIT)
                                viewModel.handle(intent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.deposit_action)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        FloatingButton(
                            enabled = uiState.isWithdrawalAvailable,
                            onClick = {
                                val intent = TransactionFormIntent.Show(Transaction.Type.WITHDRAWAL)
                                viewModel.handle(intent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.withdrawal_action)
                            )
                        }
                    }
                }

            )
            is LoadState.Failure -> ErrorMessageScreen(message = loadState.message) {
                viewModel.handle(ViewIntent.LoadAccounts(user))
            }
        }
    }
}
