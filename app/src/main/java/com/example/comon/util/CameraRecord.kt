import android.content.Context
import android.os.Environment
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.comon.R
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

@Composable
fun CameraRecord(
    context: Context,
    index: Int,
    onResult: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
        remember { ProcessCameraProvider.getInstance(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    val videoCaptureExecutor = remember { ContextCompat.getMainExecutor(context) }

    val videoCapture = remember {
        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
            .build()
        VideoCapture.withOutput(recorder)
    }

    var recording: Recording? by remember { mutableStateOf(null) }
    var result: String by remember { mutableStateOf("") }

    Box {
        CameraPreview(videoCaptureExecutor, cameraProviderFuture, videoCapture, lifecycleOwner)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                onClick = {
                    if (recording != null) {
                        recording?.stop()
                        recording = null
                    } else {
                        val videoFile = createTempFile(context)
                        val outputOptions = FileOutputOptions.Builder(videoFile).build()
                        recording = videoCapture.output
                            .prepareRecording(context, outputOptions)
                            .start(videoCaptureExecutor) { event ->
                                when (event) {
                                    is VideoRecordEvent.Start -> {
                                        // 녹화 시작됨
                                    }
                                    is VideoRecordEvent.Finalize -> {
                                        // 녹화 완료됨
                                        result = videoFile.absolutePath
                                        onResult(result)
                                    }
                                    is VideoRecordEvent.Status -> {
                                        // 녹화 상태 업데이트
                                    }
                                    is VideoRecordEvent.Pause -> {
                                        // 녹화 일시정지됨
                                    }
                                    is VideoRecordEvent.Resume -> {
                                        // 녹화 재개됨
                                    }
                                }
                            }
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
                    .width(50.dp)
                    .height(50.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon__mic_),
                    contentDescription = "Record Button"
                )
            }
        }
    }
}

@Composable
fun CameraPreview(
    videoCaptureExecutor: Executor,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    videoCapture: VideoCapture<Recorder>,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val previewView = remember {
        PreviewView(context).apply {
            var implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FILL_START
        }
    }

    val cameraProvider = cameraProviderFuture.get()

    val previewUseCase = remember {
        Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
    }
    val cameraSelector = remember {
        CameraSelector.DEFAULT_FRONT_CAMERA
    }

    LaunchedEffect(previewUseCase, videoCapture, cameraSelector) {
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                previewUseCase,
                videoCapture
            )
        } catch (e: Exception) {
            // 예외 처리
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize()
            .clip(RoundedCornerShape(16.dp)),
        factory = { previewView }
    )
}

fun createTempFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val videoFileName = "VIDEO_$timeStamp"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
    return File.createTempFile(videoFileName, ".mp4", storageDir)
}
