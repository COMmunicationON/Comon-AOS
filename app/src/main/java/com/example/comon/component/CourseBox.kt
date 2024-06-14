package com.example.comon.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.comon.DevicePreview
import com.example.comon.ui.theme.ComonTheme

@Composable
fun CourseBox(
    department: String,
    navController : NavController
) {
    val type = when(department){
        "단어" -> "word"
        "음절" -> "syllable"
        "문장" -> "sentence"
        "오답노트" -> "Note"
        else -> {"none"}
    }

    Button(
        modifier = Modifier
            .padding(5.dp)
            .shadow(
                elevation = 10.dp,
                spotColor = Color.LightGray,
                ambientColor = Color(0x0A000000),
                shape = RoundedCornerShape(size = 30.dp)
            ),
        colors = ButtonDefaults.buttonColors(Color(0xFFFDF7FF)),
        onClick = {
            //navController.currentBackStackEntry?.savedStateHandle?.set(key = "path", value = department)
            if(type=="Note")
            {
                navController.navigate("NOTE")
            }
            else {
                navController.navigate("DIFFICULTY/$type")
            }
                  },
        shape = RoundedCornerShape(size = 18.dp),
        contentPadding = PaddingValues(0.dp)

    )
    {
        Box(
            modifier = Modifier
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
                )
            )
        }

    }
//    Box(
//        modifier = Modifier
//            .shadow(
//                elevation = 10.dp,
//                spotColor = Color.LightGray,
//                ambientColor = Color(0x0A000000),
//                shape = RoundedCornerShape(size = 30.dp)
//            )
//            .padding(5.dp)
//            .width(150.dp)
//            .height(150.dp)
//            .background(
//                color = Color(0xFFFDF7FF),
//                shape = RoundedCornerShape(size = 18.dp)
//            )
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

}


@DevicePreview
@Composable
private fun CourseBoxDevicePreview() {
    val nav = rememberNavController()
    ComonTheme {
        CourseBox("음절", nav)
    }
}

