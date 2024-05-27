package com.example.comon.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.R

@Composable
fun StaticsName(
    mainName: String,
    explain: String
)
{
    Row {
        Text(
            text = mainName,
            modifier = Modifier
                .padding(start = 30.dp, top =40.dp),
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF11263C),
            )
        )
        Image(
            modifier = Modifier
                .padding(start = 10.dp, top = 40.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.alert_circle),
            contentDescription = "help"
        )
    }
    Text(
        modifier = Modifier.padding(start = 30.dp, top = 3.dp),
        text = explain,
        style = TextStyle(
            fontSize = 11.sp,
            fontWeight = FontWeight(300),
            color = Color(0xFF000000),
        )
    )
}