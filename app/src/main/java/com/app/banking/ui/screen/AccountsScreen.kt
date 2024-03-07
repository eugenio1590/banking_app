package com.app.banking.ui.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.banking.presentation.accounts.LoadState
import com.app.banking.presentation.accounts.ViewIntent
import com.app.banking.presentation.accounts.ViewModel
import com.app.banking.ui.account.search.AccountList
import com.app.banking.ui.component.FloatingButton
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
                content = { AccountList(paddingValues = it, accounts = loadState.accounts) },
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        FloatingButton(
                            enabled = uiState.isDepositAvailable,
                            onClick = { TODO("handle the deposit screen") }
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Deposit")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        FloatingButton(
                            enabled = uiState.isWithdrawalAvailable,
                            onClick = { TODO("Handle the withdrawal screen") }
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Withdrawal")
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
