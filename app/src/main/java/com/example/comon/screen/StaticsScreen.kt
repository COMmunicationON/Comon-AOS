package com.example.comon.screen

import android.media.ImageReader
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.component.StaticsName
import com.example.comon.ui.theme.ComonTheme


@Composable
fun StaticsScreen() {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(color = Color(0xFFFDFDFD))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
        {
            Box(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            )
            {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "훈련 통계",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                )
            }
            StaticsName("My Progress","나의 진척도를 나타냅니다" )
            StaticsName("Answer Percentage","전체 갯수 중 총 맞춘 갯수" )
            StaticsName("My Weakness","내가 가장 취약한 부분은?" )
            StaticsName("Train Accurancy","훈련 분야별 학습 진척도" )
        }

    }
}

@DevicePreview
@Composable
fun StaticsPreview()
{
    ComonTheme {
        StaticsScreen()
    }
}