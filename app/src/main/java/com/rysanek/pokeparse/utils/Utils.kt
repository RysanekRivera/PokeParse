package com.rysanek.pokeparse.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rysanek.pokeparse.R

fun AppCompatActivity.setUpSystemWindow(){
    val navBarColor = ContextCompat.getColor(this.applicationContext, R.color.navigation_bar_color)
    val statusBarColor = ContextCompat.getColor(this.applicationContext, R.color.status_bar_color)
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
        this.window.setDecorFitsSystemWindows(false)
        this.window.navigationBarColor = navBarColor
        this.window.statusBarColor = statusBarColor
    }
}

fun Fragment.showSnackBar(message: String, length: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(requireView(), message, length)
        .setAction("OK"){}
        .show()
}

fun Fragment.hideKeyboard(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        requireActivity().window.insetsController?.hide(WindowInsets.Type.ime())
    } else {
        val manager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(this.requireView().windowToken, 0)
    }
}

fun View.hide(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}