package com.example.comon.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.ui.theme.ComonTheme

@Composable
fun MenuItem(
    title: String,
    onPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPress() }
            .padding(horizontal = 28.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Color(0x99171717), fontSize = 15.sp)
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.chevron_left),
            contentDescription = "Decoration",
            modifier = Modifier.rotate(180f)
        )
    }
}

@DevicePreview
@Composable
private fun MenuItemDevicePreview() {
    ComonTheme {
        MenuItem(title = "공지사항", onPress = { })
    }
}