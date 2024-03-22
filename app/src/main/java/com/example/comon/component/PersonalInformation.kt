package com.example.comon.component


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.comon.DevicePreview
import com.example.comon.ui.theme.ComonTheme

@Composable
fun PersonalInformation(
    name: String,
    email: String
) {
    Column {
        Text(text = name, fontSize = 20.sp)
        Text(text = email, color = Color(0xFFCBC7C7), fontSize = 15.sp)
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonalInformationPreview() {
    ComonTheme {
        PersonalInformation(
            name = "최상",
            email = "sang8408@gmail.com"
        )
    }
}

@DevicePreview
@Composable
private fun PersonalInformationDevicePreview() {
    ComonTheme {
        PersonalInformation(
            name = "최상",
            email = "sang8408@gmail.com"
        )
    }
}