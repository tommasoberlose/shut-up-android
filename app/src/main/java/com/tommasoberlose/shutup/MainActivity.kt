package com.tommasoberlose.shutup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.content.pm.ShortcutManagerCompat
import android.R.attr.label
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.graphics.drawable.IconCompat



class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (intent.action == ShutUpReceiver.Companion.ACTION_SHUT_UP) {
      ShutUpReceiver.shutUpThisPhone(this)
      finish()
    } else if (intent.action == Intent.ACTION_CREATE_SHORTCUT) {
      val shortcutIntent = Intent(this, MainActivity::class.java)
      shortcutIntent.action = ShutUpReceiver.ACTION_SHUT_UP
      val shortcut = ShortcutInfoCompat.Builder(this, "shut_up")
          .setIntent(shortcutIntent)
          .setShortLabel(getString(R.string.app_name))
          .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher))
          .build()
      ShortcutManagerCompat.requestPinShortcut(this, shortcut, null)
      finish()
    }

    setContentView(R.layout.activity_main)
  }
}
