package com.app.banking.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.banking.ui.theme.BankingAppTheme

@Composable
fun FloatingButton(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(56.dp),
        enabled = enabled,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun FloatingButtonDefaultPreview() {
    BankingAppTheme {
        FloatingButton(enabled = true, onClick = { }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Add to Favorite")
        }
    }
}
