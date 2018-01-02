package com.tommasoberlose.shutup.service

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GhostActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    Log.d("SHUT_UP", "3")

    ShutUpReceiver.shutUpThisPhone(this)
    finish()
  }
}
