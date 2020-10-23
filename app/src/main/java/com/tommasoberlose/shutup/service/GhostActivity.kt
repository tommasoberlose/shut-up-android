package com.tommasoberlose.shutup.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GhostActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ShutUpReceiver.shutUpThisPhone(this)
    finish()
  }
}
