package com.example.comon.util

import RequestRecordAudioPermission
import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.microsoft.cognitiveservices.speech.audio.PullAudioInputStreamCallback
import com.microsoft.cognitiveservices.speech.audio.AudioStreamFormat

/**
 * MicrophoneStream exposes the Android Microphone as an PullAudioInputStreamCallback
 * to be consumed by the Speech SDK.
 * It configures the microphone with 16 kHz sample rate, 16 bit samples, mono (single-channel).
 */

class MicrophoneStream(private val context: Context) : PullAudioInputStreamCallback() {
    private val SAMPLE_RATE = 16000
    private val format: AudioStreamFormat = AudioStreamFormat.getWaveFormatPCM(SAMPLE_RATE.toLong(), 16.toShort(), 1.toShort())
    private var recorder: AudioRecord? = null

    init {
        InitMic()
    }

    override fun read(bytes: ByteArray): Int {
        return if (this.recorder != null) {
            val ret: Long = this.recorder!!.read(bytes, 0, bytes.size).toLong()
            ret.toInt()
        } else {
            0
        }
    }

    override fun close() {
        this.recorder?.release()
        this.recorder = null
    }

    private fun InitMic() {
        val af = AudioFormat.Builder()
            .setSampleRate(SAMPLE_RATE)
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
            .build()

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        recorder = AudioRecord.Builder()
            .setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
            .setAudioFormat(af)
            .build()

        recorder?.startRecording()
    }
}
