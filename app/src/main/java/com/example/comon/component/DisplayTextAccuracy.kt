import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.ui.theme.AppColors

@Composable
fun DisplayTextAccuracy(word: List<String>, accuracies: List<Int>) {
    Row {
        Text("/ ", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        for (i in word.indices) {
            Text(word[i], color = getColor(accuracies[i]), fontWeight = FontWeight.Bold, fontSize = 17.sp)
            if (i < word.size - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Text(" /", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 22.sp)
    }
}


@Composable
private fun getColor(accuracy: Int): Color {
    return when {
        accuracy < 60 -> AppColors.mRed
        accuracy < 80 -> AppColors.mYellow
        else -> AppColors.mGreen
    }
}

// Usage
@Preview
@Composable
fun PreviewWordDisplay() {
    val word = listOf("ㅅ", "ㅜ", "ㅂ", "ㅏ", "ㄱ")
    val accuracies = listOf(90,70,80,60,40)
    DisplayTextAccuracy(word, accuracies)
}
