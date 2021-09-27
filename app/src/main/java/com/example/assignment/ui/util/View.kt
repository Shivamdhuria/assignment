package com.example.assignment.ui.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(resourceId: Int) {
    Toast.makeText(activity, getString(resourceId), Toast.LENGTH_SHORT).show()
}