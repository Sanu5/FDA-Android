package com.example.fda_android.utils

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import androidx.databinding.BindingAdapter

class BindingAdapterUtils {

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["bindingStartColor", "bindingEndColor", "bindingRadius",
            "bindingTopLeftRadius", "bindingTopRightRadius", "bindingBottomLeftRadius",
            "bindingBottomRightRadius", "bindingStrokeColor", "bindingStrokeWidth", "bindingShape",
            "bindingOrientation", "bindingCenterColor", "bindingStrokeColors", "bindingGradientColors"], requireAll = false)
        fun setBackground(view: View, startColor: Int? = null, endColor: Int? = null,
                          radius: Float = 0f, topLeftRadius : Float = 0f, topRightRadius : Float = 0f,
                          bottomLeftRadius : Float = 0f, bottomRightRadius : Float = 0f,
                          strokeColor: Int? = null, strokeWidth: Float? = null,
                          shape: Int? = null, orientation: GradientDrawable.Orientation? = null, centerColor: Int? = null, strokeColors: List<Int>? = null, gradientColors: List<Int>? = null) {

            view.let {
                val drawable = GradientDrawable()
                if (topLeftRadius > 0 || topRightRadius > 0 || bottomLeftRadius > 0 || bottomRightRadius > 0)
                    drawable.cornerRadii = floatArrayOf(topLeftRadius, topLeftRadius, topRightRadius,
                        topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius)
                if (radius > 0)
                    drawable.cornerRadius = radius
                drawable.orientation = orientation ?: GradientDrawable.Orientation.LEFT_RIGHT
                drawable.shape = shape ?: GradientDrawable.RECTANGLE
                if (startColor != null || centerColor != null || endColor != null) {
                    val colorsList = ArrayList<Int>()
                    startColor?.let {
                        colorsList.add(it)
                    }
                    centerColor?.let {
                        colorsList.add(it)
                    }
                    endColor?.let {
                        colorsList.add(it)
                    }
                    if (colorsList.size == 1) {
                        drawable.setColor(colorsList.first())
                    } else {
                        drawable.colors = colorsList.toIntArray()
                    }
                } else if (gradientColors != null) {
                    drawable.colors = gradientColors.toIntArray()
                }

                if (!strokeColors.isNullOrEmpty()) {
                    val strokeWidthInt = strokeWidth?.toInt() ?: 1
                    val drawableStroke = GradientDrawable().apply {
                        if (topLeftRadius > 0 || topRightRadius > 0 || bottomLeftRadius > 0 || bottomRightRadius > 0) {
                            this.cornerRadii = floatArrayOf(topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius)
                        }
                        if (radius > 0) this.cornerRadius = radius
                        this.orientation = orientation ?: GradientDrawable.Orientation.LEFT_RIGHT
                        this.shape = shape ?: GradientDrawable.RECTANGLE
                        this.colors = strokeColors.toIntArray()
                    }
                    val finalDrawable = LayerDrawable(arrayOf<Drawable>(drawableStroke, drawable))
                    finalDrawable.setLayerInset(0, 0, 0, 0, 0)
                    finalDrawable.setLayerInset(1, strokeWidthInt, strokeWidthInt, strokeWidthInt, strokeWidthInt)
                    it.background = finalDrawable
                } else {
                    if (strokeColor != null) {
                        if (strokeWidth == null) {
                            drawable.setStroke(1, strokeColor)
                        } else {
                            drawable.setStroke(strokeWidth.toInt(), strokeColor)
                        }
                    }
                    it.background = drawable
                }
            }
        }
    }
}
