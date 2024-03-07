package com.app.banking.ui.transaction.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.banking.R
import com.app.domain.model.Account
import com.app.domain.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionForm(
    type: Transaction.Type,
    accounts: List<Account>,
    onPerform: (Double, Account) -> Unit,
    onCancel: () -> Unit
) {
    var amount: Double by remember { mutableDoubleStateOf(0.0) }
    var selectedAccount: Account? by remember { mutableStateOf(null) }
    var isExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        title = { Text(text = type.name) },
        text = {
            Column {
                TextField(
                    value = amount.toString(),
                    onValueChange = { amount = it.toDouble() },
                    label = { Text(stringResource(id = R.string.amount_label)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(15.dp))

                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = !isExpanded }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedAccount?.toString() ?: "",
                        onValueChange = {},
                        label = { Text(stringResource(id = R.string.select_account_label)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        accounts.forEach { account ->
                            DropdownMenuItem(
                                text = { Text(account.toString()) },
                                onClick = {
                                    selectedAccount = account
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        onDismissRequest = { onCancel() },
        confirmButton = {
            TextButton(onClick = { selectedAccount?.let { onPerform(amount, it) } }) {
                Text(text = stringResource(id = R.string.confirm_action))
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        }
    )
}