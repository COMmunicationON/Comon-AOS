package com.example.comon.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.R
import com.example.comon.ui.theme.ComonTheme

@Composable
fun MyProgressBanner(
    modifier: Modifier
) {
    Row (modifier= modifier,
        horizontalArrangement = Arrangement.Center){
        Box(modifier = Modifier)
        {
            Image(
                painter = painterResource(id = R.drawable.icon_progress_back),
                contentDescription = "",
                modifier = Modifier
                    .shadow(
                        elevation = 12.307692527770996.dp,
                        spotColor = Color(0x0F323247),
                        ambientColor = Color(0x0F323247)
                    )
                    .shadow(
                        elevation = 6.153846263885498.dp,
                        spotColor = Color(0x14323247),
                        ambientColor = Color(0x14323247)
                    )
            )
            Row(modifier = Modifier.padding(start = 21.dp, top = 5.dp))
            {
                CircularProgressbar(
                    size = 70.dp,
                    indicatorThickness = 10.dp,
                    dataTextStyle = TextStyle(fontSize = 20.sp)
                )
                Text(
                    text = "쉬움",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MyProgressBannerPreview() {
    ComonTheme {
        MyProgressBanner(modifier = Modifier)
    }
}