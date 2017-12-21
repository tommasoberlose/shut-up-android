package com.tommasoberlose.shutup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class GhostActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    ShutUpReceiver.shutUpThisPhone(this)
    finish()
  }
}
