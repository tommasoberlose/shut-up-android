package com.tommasoberlose.shutup.service

import android.content.Intent
import android.service.quicksettings.TileService
import android.os.Build
import android.annotation.TargetApi
import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import com.tommasoberlose.shutup.R


/**
 * Created by tommaso on 04/12/17.
 */

@SuppressLint("Override")
@TargetApi(Build.VERSION_CODES.N)
class QSIntentService : TileService() {


  override fun onStartListening() {
    val tile: Tile = qsTile
    tile.icon = Icon.createWithResource(this, R.drawable.ic_quick_settings_shut_up)
    tile.updateTile()
    super.onStartListening()
  }

  override fun onClick() {

    // Check to see if the device is currently locked.
    val isCurrentlyLocked = this.isLocked

    if (!isCurrentlyLocked) {
      val intent = Intent(this, GhostActivity::class.java)
      intent.action = ShutUpReceiver.ACTION_SHUT_UP
      intent.flags += Intent.FLAG_ACTIVITY_NEW_TASK
      startActivityAndCollapse(intent)
    }
  }

}