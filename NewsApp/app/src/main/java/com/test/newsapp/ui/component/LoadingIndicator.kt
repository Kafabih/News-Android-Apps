package com.test.newsapp.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    backgroundColour: Color = Color.White.copy(alpha = 0.4f),
    @StringRes
    loadingText: Int,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColour),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = loadingText))
        CircularProgressIndicator(
            color = Color.Black,
        )
    }
}
