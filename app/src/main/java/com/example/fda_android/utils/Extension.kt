package com.example.fda_android.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.fda_android.R

fun FragmentTransaction.openFragment(
    fragment: Fragment, containerId: Int, tag: String,
    addToBackStack: Boolean = false, add: Boolean = false, animate: Boolean = false
) {
    if (animate) setCustomAnimations(R.anim.enter_right_new_screen, 0, R.anim.enter_left_new_screen, 0)
    if (add) {
        add(containerId, fragment, tag)
    } else {
        replace(containerId, fragment, tag)
    }
    if (addToBackStack)
        addToBackStack(tag)
    commitAllowingStateLoss()
}