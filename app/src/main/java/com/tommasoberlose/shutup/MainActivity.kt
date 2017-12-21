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
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.graphics.drawable.IconCompat
import android.view.View
import kotlinx.android.synthetic.main.main_menu_layout.view.*


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (intent.action == ShutUpReceiver.Companion.ACTION_SHUT_UP) {
      ShutUpReceiver.shutUpThisPhone(this)
      finish()
    } else if (intent.action == Intent.ACTION_CREATE_SHORTCUT) {
      val shortcutIntent = Intent(this, GhostActivity::class.java)
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

    action_menu.setOnClickListener {
      showMenu()
    }

  }

  fun showMenu() {
    val mBottomSheetDialog = BottomSheetDialog(this)
    val menuView: View = View.inflate(this, R.layout.main_menu_layout, null)

    menuView.action_share.setOnClickListener {
      Util.share(this@MainActivity)
      mBottomSheetDialog.dismiss()
    }

    menuView.action_rate.setOnClickListener {
      Util.rateApp(this@MainActivity, "https://play.google.com/store/apps/details?id=com.tommasoberlose.shutup")
      mBottomSheetDialog.dismiss()
    }

    menuView.action_feedback.setOnClickListener {
      Util.sendEmail(this@MainActivity)
      mBottomSheetDialog.dismiss()
    }

    mBottomSheetDialog.setContentView(menuView)
    mBottomSheetDialog.show()
  }
}
