package com.challenge.carapp.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager


fun Activity.closeKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}