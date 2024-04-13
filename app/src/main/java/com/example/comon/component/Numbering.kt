package com.example.comon.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.DevicePreview
import com.example.comon.ui.theme.ComonTheme

@Composable
fun Numbering(
    num: Int,
    active: Boolean
)
{
    val backgroundColor = if (active) Color(0xFF6F3BDD) else Color.Gray
    val diameter = if (active) 35.dp else 20.dp
    val size = if (active) 15.sp else 10.sp

    Box(
        modifier = Modifier
            .padding(0.5.dp)
            .size(diameter)
            .background(color = backgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = num.toString(),
            style = TextStyle(
                fontSize = size,
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@DevicePreview
@Composable
private fun NumberingDevicePreview() {
    ComonTheme {
        Numbering(1, false)
    }
}