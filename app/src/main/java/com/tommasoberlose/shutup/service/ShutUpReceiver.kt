package com.tommasoberlose.shutup.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.util.Log
import com.tommasoberlose.shutup.util.ConfigManager
import com.tommasoberlose.shutup.util.ToastUtil

class ShutUpReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    Log.d("SHUT_UP", "OK")
    if (intent.action == ACTION_SHUT_UP) {
      val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_ALARM)) {
        silenceStrem(audioManager, AudioManager.STREAM_ALARM)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_MUSIC)) {
        silenceStrem(audioManager, AudioManager.STREAM_MUSIC)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_NOTIFICATION)) {
        silenceStrem(audioManager, AudioManager.STREAM_NOTIFICATION)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_RING)) {
        silenceStrem(audioManager, AudioManager.STREAM_RING)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_SYSTEM)) {
        silenceStrem(audioManager, AudioManager.STREAM_SYSTEM)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_VOICE_CALL)) {
        silenceStrem(audioManager, AudioManager.STREAM_VOICE_CALL)
      }
      ToastUtil.showCustomView(context)
    }
  }

  fun silenceStrem(audioManager: AudioManager, stream: Int) {
    try {
      audioManager.setStreamVolume(stream, 0, 0)
    } catch (ignored: Exception) { }
  }

  companion object {
    val ACTION_SHUT_UP = "com.tommasoberlose.shutup.action.SHUT_UP"

    fun shutUpThisPhone(context: Context) {
      val i = Intent(ACTION_SHUT_UP)
      i.setClass(context, ShutUpReceiver::class.java)
      context.sendBroadcast(i)
    }
  }
}
