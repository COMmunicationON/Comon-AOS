import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch

@Composable
fun RequestRecordAudioPermission() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val permissionGranted = remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted.value = isGranted
        if (isGranted) {
            // Permission is granted, proceed with recording audio
        } else {
            // Permission is denied, handle accordingly
        }
    }

    LaunchedEffect(Unit) {
        when (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)) {
            PackageManager.PERMISSION_GRANTED -> {
                permissionGranted.value = true
                // Permission is already granted, proceed with recording audio
            }
            PackageManager.PERMISSION_DENIED -> {
                // Request the permission
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    if (permissionGranted.value) {
        // Permission granted UI
        Text(text = "Microphone permission granted")
    } else {
        // Button to request permission
        Button(onClick = {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }) {
            Text(text = "Request Microphone Permission")
        }
    }
}
