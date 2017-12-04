package com.tommasoberlose.shutup

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.widget.Toast

class ShutUpReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == ACTION_SHUT_UP) {
      val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
      audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
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
