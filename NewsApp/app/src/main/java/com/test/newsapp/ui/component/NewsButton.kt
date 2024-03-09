package com.inventara.mobile.akujual.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inventara.mobile.akujual.ui.theme.Purple200

@Composable
fun CustomButton(
    color: Color,
    text: String,
    textColor: Color = Color.White,
    isEnable: Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 24.dp),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
             color, contentColor = Color.White
        ),
        enabled = isEnable
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 4.dp),
            color = textColor ?: Color.White
        )
    }
}

@Preview
@Composable
private fun CustomButton() {
    CustomButton(Purple200, "Login") {

    }
}