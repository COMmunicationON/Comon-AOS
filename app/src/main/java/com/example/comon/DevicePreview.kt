package com.example.comon

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
    (
    name = "Galaxy S23 Ultra",
    showBackground = true,
    device = "spec:width=360dp,height=772dp,dpi=411"
)
@Preview(
    name = "Galaxy S23",
    showBackground = true,
    device = "spec:width=360dp,height=800dp,dpi=500"
)

@Preview
    (
    name = "pixel 3",
    showSystemUi = true,
    device = "id:pixel_3"
            )
@Preview(
    name="6.7in Foldable",
    showSystemUi = true,
    device = "id:6.7in Foldable")


annotation class DevicePreview

