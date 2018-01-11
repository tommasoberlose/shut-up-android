package com.tommasoberlose.shutup.ui

import android.Manifest.permission.INSTALL_SHORTCUT
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.graphics.drawable.IconCompat
import android.view.View
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
import android.media.AudioManager
import android.view.animation.AccelerateDecelerateInterpolator
import com.tommasoberlose.shutup.constants.Constants
import com.tommasoberlose.shutup.util.ConfigManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_menu_layout.view.*
import android.app.PendingIntent




class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (intent.action == Intent.ACTION_CREATE_SHORTCUT) {
      val shortcutIntent = Intent(this, GhostActivity::class.java)
      shortcutIntent.action = ShutUpReceiver.ACTION_SHUT_UP
      val shortcut = ShortcutInfoCompat.Builder(this, "shut_up")
          .setIntent(shortcutIntent)
          .setShortLabel(getString(R.string.app_name))
          .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_shortcut))
          .setLongLabel(getString(R.string.app_name))
          .build()

      val pinnedShortcutCallbackIntent = ShortcutManagerCompat.createShortcutResultIntent(this, shortcut)
      setResult(Activity.RESULT_OK, pinnedShortcutCallbackIntent)
      finish()
    } else if (intent.action == ShutUpReceiver.ACTION_SHUT_UP) {
      startActivity(Intent(this, GhostActivity::class.java))
      finish()
    }

    setContentView(R.layout.activity_main)
    setupConfig()

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

  override fun onBackPressed() {
    if (config_layout.visibility == View.VISIBLE) {
      closeConfig()
    } else {
      super.onBackPressed()
    }
  }

  fun setupConfig() {
    action_stream_audio.setOnClickListener {
      ConfigManager.toggleStreamConfig(this, AudioManager.STREAM_ALARM)
      updateUI()
    }

    action_stream_music.setOnClickListener {
      ConfigManager.toggleStreamConfig(this, AudioManager.STREAM_MUSIC)
      updateUI()
    }

    action_stream_notification.setOnClickListener {
      ConfigManager.toggleStreamConfig(this, AudioManager.STREAM_NOTIFICATION)
      updateUI()
    }

    action_stream_ring.setOnClickListener {
      ConfigManager.toggleStreamConfig(this, AudioManager.STREAM_RING)
      updateUI()
    }

    action_stream_system.setOnClickListener {
      ConfigManager.toggleStreamConfig(this, AudioManager.STREAM_SYSTEM)
      updateUI()
    }

    action_stream_voice_call.setOnClickListener {
      ConfigManager.toggleStreamConfig(this, AudioManager.STREAM_VOICE_CALL)
      updateUI()
    }

    updateUI()
  }

  fun updateUI() {
    action_stream_audio.alpha = if (ConfigManager.isStreamSelected(this, AudioManager.STREAM_ALARM)) 1F else 0.5F
    action_stream_music.alpha = if (ConfigManager.isStreamSelected(this, AudioManager.STREAM_MUSIC)) 1F else 0.5F
    action_stream_notification.alpha = if (ConfigManager.isStreamSelected(this, AudioManager.STREAM_NOTIFICATION)) 1F else 0.5F
    action_stream_ring.alpha = if (ConfigManager.isStreamSelected(this, AudioManager.STREAM_RING)) 1F else 0.5F
    action_stream_system.alpha = if (ConfigManager.isStreamSelected(this, AudioManager.STREAM_SYSTEM)) 1F else 0.5F
    action_stream_voice_call.alpha = if (ConfigManager.isStreamSelected(this, AudioManager.STREAM_VOICE_CALL)) 1F else 0.5F
  }

  private fun showConfig() {
    val cy = config_layout.height
    val finalRadius = Math.hypot(config_layout.width.toDouble(), config_layout.height.toDouble()).toFloat()
    val anim = ViewAnimationUtils.createCircularReveal(config_layout, config_layout.width / 2, cy, 0f, finalRadius)
    config_layout.visibility = View.VISIBLE
    anim.interpolator = AccelerateDecelerateInterpolator()
    anim.duration = 400
    anim.start()
  }

  private fun closeConfig() {
    val cy = config_layout.height
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

    anim.start()
  }

  private fun showMenu() {
    val mBottomSheetDialog = BottomSheetDialog(this)
    val menuView: View = View.inflate(this, R.layout.main_menu_layout, null)

    menuView.action_project.setOnClickListener {
      Util.openURI(this@MainActivity, "https://github.com/tommasoberlose/shut-up-android")
      mBottomSheetDialog.dismiss()
    }

    menuView.action_author.setOnClickListener {
      Util.openURI(this@MainActivity, "http://tommasoberlose.com/")
      mBottomSheetDialog.dismiss()
    }

    menuView.action_share.setOnClickListener {
      Util.share(this@MainActivity)
      mBottomSheetDialog.dismiss()
    }

    menuView.action_rate.setOnClickListener {
      Util.openAppURI(this@MainActivity, "https://play.google.com/store/apps/details?id=com.tommasoberlose.shutup")
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
