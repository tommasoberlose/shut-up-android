package com.tommasoberlose.shutup.util

import android.content.Context
import android.widget.Toast
import android.view.Gravity
import android.view.View
import com.tommasoberlose.shutup.R


/**
 * Created by tommaso on 04/12/17.
 */
object ToastUtil {

  fun showCustomView(context: Context) {
    val customLayout = View.inflate(context, R.layout.toast_layout, null)

    val toast = Toast(context)
    toast.setGravity(Gravity.TOP, 0, 50)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = customLayout
    toast.show()
  }

}