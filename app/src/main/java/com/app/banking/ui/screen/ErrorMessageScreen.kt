package com.app.banking.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.banking.R
import com.app.banking.presentation.ErrorMessage

@Composable
fun ErrorMessageScreen(message: ErrorMessage, tryAgain: () -> Unit) {
    if (message.image != null) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = message.image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
    //.........................Text: title
    Text(
        text = "Whoops!!",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        letterSpacing = 2.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.height(8.dp))

    //.........................Text : description
    Text(
        //text = "No Internet connection was found. Check your connection or try again.",
        text = stringResource(id = message.message),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
            .fillMaxWidth(),
        letterSpacing = 1.sp,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
    )
    //.........................Spacer
    Spacer(modifier = Modifier.height(24.dp))

    Button(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(),
        onClick = tryAgain
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontSize = 20.sp,
                color = Color.White,
                text = stringResource(id = R.string.try_again_action)
            )
        }
    }
}
