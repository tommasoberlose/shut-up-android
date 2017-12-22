package com.tommasoberlose.shutup.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import com.tommasoberlose.shutup.util.ConfigManager
import com.tommasoberlose.shutup.util.ToastUtil

class ShutUpReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == ACTION_SHUT_UP) {
      val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_ALARM)) {
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_MUSIC)) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_NOTIFICATION)) {
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_RING)) {
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_SYSTEM)) {
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0)
      }
      if (ConfigManager.isStreamSelected(context, AudioManager.STREAM_VOICE_CALL)) {
        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, 0)
      }
      ToastUtil.showCustomView(context)
    }
  }

  companion object {
    val ACTION_SHUT_UP = "com.tommasoberlose.shutup.action.SHUT_UP"

    fun shutUpThisPhone(context: Context) {
      context.sendBroadcast(Intent(ACTION_SHUT_UP))
    }
  }
}
