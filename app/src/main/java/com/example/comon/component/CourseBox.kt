package com.example.comon.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.ui.theme.ComonTheme

@Composable
fun CourseBox(
    department: String
) {
//    Button(
//        modifier = Modifier
//            .shadow(
//                elevation = 10.dp,
//                spotColor = Color.LightGray,
//                ambientColor = Color(0x0A000000)
//            )
//            .padding(5.dp)
//            .width(150.dp)
//            .height(150.dp)
//            .background(
//                color = Color(0xFFFDF7FF),
//                shape = RoundedCornerShape(size = 18.dp)
//            ),
//        onClick = { /*TODO*/ },
//    )
//    {
//        Text(
//            text = department,
//            modifier = Modifier.padding(20.dp),
//            style = TextStyle(
//                fontSize = 20.sp,
//                fontWeight = FontWeight(600),
//                color = Color(0xFF000000),
//                textAlign = TextAlign.Center,
//            )
//        )
//    }
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                spotColor = Color.LightGray,
                ambientColor = Color(0x0A000000),
                shape = RoundedCornerShape(size = 30.dp)
            )
            .padding(5.dp)
            .width(150.dp)
            .height(150.dp)
            .background(
                color = Color(0xFFFDF7FF),
                shape = RoundedCornerShape(size = 18.dp)
            )
    )
    {
        Text(
            text = department,
            modifier = Modifier.padding(20.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
    }

}

@DevicePreview
@Composable
private fun CourseBoxDevicePreview() {
    ComonTheme {
        CourseBox("음절")
    }
}