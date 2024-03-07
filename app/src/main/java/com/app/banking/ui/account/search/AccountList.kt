package com.app.banking.ui.account.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.app.domain.model.Account

@Composable
fun AccountList(
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    accounts: List<Account>
) {
    val listState = rememberLazyListState()
    LazyColumn(contentPadding = paddingValues, state = listState) {
        items(accounts) {item ->
            AccountCard(account = item)
        }
    }
}
