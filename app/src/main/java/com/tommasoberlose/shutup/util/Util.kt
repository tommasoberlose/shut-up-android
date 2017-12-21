package com.tommasoberlose.shutup.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.tommasoberlose.shutup.R

/**
 * Created by tommaso on 21/12/17.
 */

object Util {


  fun rateApp(context: Context, url: String) {
    val openIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(openIntent)
  }

  fun share(context: Context) {
    val sendIntent = Intent(Intent.ACTION_SEND)
    sendIntent.putExtra(Intent.EXTRA_TEXT, "Yep, shut up your phone: https://play.google.com/store/apps/details?id=com.tommasoberlose.shutup")
    sendIntent.type = "text/plain"
    context.startActivity(Intent.createChooser(sendIntent, context.getString(R.string.action_share)))
  }

  fun sendEmail(context: Context) {
    val i = Intent(Intent.ACTION_SEND)
    i.type = "message/rfc822"
    i.putExtra(Intent.EXTRA_EMAIL, arrayOf("tommaso.berlose@gmail.com"))
    i.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.feedback_title))
    try {
      context.startActivity(Intent.createChooser(i, context.getString(R.string.feedback_chooser_title)))
    } catch (ex: Exception) {
      Toast.makeText(context, R.string.feedback_error, Toast.LENGTH_SHORT).show();
    }
  }
}