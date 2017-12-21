package com.tommasoberlose.shutup.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.graphics.drawable.IconCompat
import android.view.View
import kotlinx.android.synthetic.main.main_menu_layout.view.*
import android.widget.Toast
import android.text.TextUtils
import com.tommasoberlose.shutup.service.GhostActivity
import com.tommasoberlose.shutup.R
import com.tommasoberlose.shutup.service.ShutUpReceiver
import com.yarolegovich.lovelydialog.LovelyChoiceDialog
import com.tommasoberlose.shutup.util.Util
import android.view.ViewAnimationUtils
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.animation.AccelerateDecelerateInterpolator


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (intent.action == Intent.ACTION_CREATE_SHORTCUT) {
      val shortcutIntent = Intent(this, GhostActivity::class.java)
      shortcutIntent.action = ShutUpReceiver.ACTION_SHUT_UP
      val shortcut = ShortcutInfoCompat.Builder(this, "shut_up")
          .setIntent(shortcutIntent)
          .setShortLabel(getString(R.string.app_name))
          .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher))
          .build()
      ShortcutManagerCompat.requestPinShortcut(this, shortcut, null)

      val result = Intent(android.content.Intent.ACTION_CREATE_SHORTCUT)
      result.putExtra(Intent.EXTRA_SHORTCUT_INTENT, Intent(this, GhostActivity::class.java))
      result.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name))
      result.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher))
      setResult(Activity.RESULT_OK, result)
      finish()
    }

    setContentView(R.layout.activity_main)

    action_menu.setOnClickListener {
      showMenu()
    }

    action_configure.setOnClickListener {
      showConfig()
    }

    action_close_config.setOnClickListener {
      closeConfig()
    }

  }

  fun showConfig() {
    val cy = config_layout.height - action_configure.height
    val finalRadius = Math.hypot(config_layout.width.toDouble(), config_layout.height.toDouble()).toFloat()
    val anim = ViewAnimationUtils.createCircularReveal(config_layout, config_layout.width / 2, cy, 0f, finalRadius)
    config_layout.visibility = View.VISIBLE
    anim.interpolator = AccelerateDecelerateInterpolator()
    anim.duration = 400
    anim.start()
  }

  fun closeConfig() {
    val cy = config_layout.height - action_configure.height
    val initialRadius = Math.hypot(config_layout.width.toDouble(), config_layout.height.toDouble()).toFloat()
    val anim = ViewAnimationUtils.createCircularReveal(config_layout, config_layout.width / 2, cy, initialRadius, 0f)
    anim.interpolator = AccelerateDecelerateInterpolator()
    anim.duration = 400
    anim.addListener(object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        config_layout.visibility = View.INVISIBLE
      }
    })

// start the animation
    anim.start()

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
