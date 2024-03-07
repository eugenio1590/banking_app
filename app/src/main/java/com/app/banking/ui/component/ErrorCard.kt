package com.app.banking.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorCard(message: String) {
    Box(
        modifier = Modifier.background(Color.Red).padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = Color.White)

            Text(
                text = message,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp).weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun ErrorCardDefaultPreview() {
    ErrorCard("Error Message.")
}
