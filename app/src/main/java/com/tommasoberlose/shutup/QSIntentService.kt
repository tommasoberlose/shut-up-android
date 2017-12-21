package com.tommasoberlose.shutup

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.os.Build
import android.annotation.TargetApi
import android.annotation.SuppressLint



/**
 * Created by tommaso on 04/12/17.
 */

@SuppressLint("Override")
@TargetApi(Build.VERSION_CODES.N)
class QSIntentService : TileService() {

  override fun onClick() {

    // Check to see if the device is currently locked.
    val isCurrentlyLocked = this.isLocked

    if (!isCurrentlyLocked) {
      val intent = Intent(this, GhostActivity::class.java)
      intent.action = ShutUpReceiver.ACTION_SHUT_UP
      startActivityAndCollapse(intent)
    }
  }

}