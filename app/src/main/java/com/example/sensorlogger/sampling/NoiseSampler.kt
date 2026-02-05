package com.example.sensorlogger.sampling

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlin.math.log10
import kotlin.math.sqrt

class NoiseSampler {

    /**
     * Zwraca przybliżone 0..100 (umowny) poziom hałasu.
     * (RMS -> dBFS -> przeskalowanie)
     */
    suspend fun sampleDb(durationMs: Long = 1500L): Float {
        val sampleRate = 44100
        val bufSize = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ).coerceAtLeast(sampleRate / 10)

        val record = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufSize
        )

        val buffer = ShortArray(bufSize)
        record.startRecording()

        val start = System.currentTimeMillis()
        var sumSquares = 0.0
        var count = 0L

        try {
            while (System.currentTimeMillis() - start < durationMs) {
                val read = record.read(buffer, 0, buffer.size)
                if (read > 0) {
                    for (i in 0 until read) {
                        val v = buffer[i].toDouble()
                        sumSquares += v * v
                        count++
                    }
                }
            }
        } finally {
            record.stop()
            record.release()
        }

        if (count == 0L) return 0f

        val rms = sqrt(sumSquares / count)
        val dbfs = 20f * log10((rms / 32767.0).coerceAtLeast(1e-9)).toFloat()
        return (dbfs + 90f).coerceIn(0f, 100f)
    }
}
