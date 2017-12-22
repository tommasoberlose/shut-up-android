package com.tommasoberlose.shutup.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.media.AudioManager
import android.preference.PreferenceManager
import com.tommasoberlose.shutup.constants.Constants

/**
 * Created by tommaso on 22/12/17.
 */
object ConfigManager {
  fun isStreamSelected(context: Context, stream: Int): Boolean {
    val SP = PreferenceManager.getDefaultSharedPreferences(context)
    return SP.getBoolean(when (stream) {
      AudioManager.STREAM_MUSIC -> Constants.CONFIG_STREAM_MUSIC
      AudioManager.STREAM_NOTIFICATION -> Constants.CONFIG_STREAM_NOTIFICATION
      AudioManager.STREAM_RING -> Constants.CONFIG_STREAM_RING
      AudioManager.STREAM_SYSTEM -> Constants.CONFIG_STREAM_SYSTEM
      AudioManager.STREAM_VOICE_CALL -> Constants.CONFIG_STREAM_VOICE_CALL
      else -> Constants.CONFIG_STREAM_ALARM
    },false)
  }

  @SuppressLint("ApplySharedPref")
  fun toggleStreamConfig(context: Context, stream: Int) {
    val SP = PreferenceManager.getDefaultSharedPreferences(context)

    val streamConstant = when (stream) {
      AudioManager.STREAM_MUSIC -> Constants.CONFIG_STREAM_MUSIC
      AudioManager.STREAM_NOTIFICATION -> Constants.CONFIG_STREAM_NOTIFICATION
      AudioManager.STREAM_RING -> Constants.CONFIG_STREAM_RING
      AudioManager.STREAM_SYSTEM -> Constants.CONFIG_STREAM_SYSTEM
      AudioManager.STREAM_VOICE_CALL -> Constants.CONFIG_STREAM_VOICE_CALL
      else -> Constants.CONFIG_STREAM_ALARM
    }

    SP.edit()
        .putBoolean(streamConstant, !SP.getBoolean(streamConstant,false))
        .commit()
  }
}