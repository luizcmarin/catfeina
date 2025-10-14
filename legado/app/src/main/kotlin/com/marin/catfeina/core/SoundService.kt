// =============================================================================
// Arquivo: com.marin.catfeina.core.SoundService.kt
// Descrição: Serviço para gerenciar a reprodução de sons ambientes.
// =============================================================================
package com.marin.catfeina.core

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundService @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private var mediaPlayer: MediaPlayer? = null

    fun play(@RawRes soundResId: Int, loop: Boolean = true) {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer.create(context, soundResId).apply {
            isLooping = loop
            start()
        }
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun release() {
        stop()
    }
}
