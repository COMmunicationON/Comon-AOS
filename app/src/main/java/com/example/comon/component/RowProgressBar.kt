package com.example.comon.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comon.ui.theme.ComonTheme
import com.example.comon.ui.theme.Purple40
import com.example.comon.ui.theme.Purple80
import com.example.comon.ui.theme.PurpleGrey40
import com.example.comon.ui.theme.PurpleGrey80

@Composable
fun RowProgressBar(
    progressCount: Int
) {

    val context = LocalContext.current
    var progress by remember { mutableFloatStateOf(0f) }

    /* to avoid the direct calculation of progress variable which is a Float
     and it can sometimes cause problems like it shows 0.4 to 0.400004 so, here I have use
     progressCount and we will increase and decrease it and then convert it to progress(Float)
     and then use that progress with our ProgressBar Width*/
    when (progressCount) {
        0 -> progress = 0.0f
        1 -> progress = 0.1f
        2 -> progress = 0.2f
        3 -> progress = 0.3f
        4 -> progress = 0.4f
        5 -> progress = 0.5f
        6 -> progress = 0.6f
        7 -> progress = 0.7f
        8 -> progress = 0.8f
        9 -> progress = 0.9f
        10 -> progress = 1.0f
    }

    val size by animateFloatAsState(
        targetValue = progress,
        tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp, start = 30.dp, end = 30.dp)
    ) {
        // for the text above the progressBar
        Row(
            modifier = Modifier
                .widthIn(min = 30.dp)
                .fillMaxWidth(size),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "$progress",
                color = Color.Black)
        }
        // Progress Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(17.dp)
        ) {
            // for the background of the ProgressBar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Purple80)
            )
            // for the progress of the ProgressBar
            Box(
                modifier = Modifier
                    .fillMaxWidth(size)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Purple40)
                    .animateContentSize()
            )
        }

//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 30.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            // decrease button
//            OutlinedButton(onClick = {
//                if (progressCount > 0) {
//                    progressCount -= 2
//                } else {
//                    Toast.makeText(context, "You cannot decrease any more", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }) {
//                Text(text = "Decrease")
//            }
//            // increase Button
//            Button(onClick = {
//                if (progressCount < 10) {
//                    progressCount += 2
//                } else {
//                    Toast.makeText(context, "You cannot increase more", Toast.LENGTH_SHORT).show()
//                }
//            }) {
//                Text(text = "Increase")
//            }
//        }
//
//
//    }

        //Use this when you want your progress bar should animate when you open your app
//        LaunchedEffect(key1 = true) {
//            progressCount = 7
//        }
    }
}

@Preview
@Composable
fun RowProgressBarPreview()
{
    ComonTheme {
        RowProgressBar(7)
    }
}