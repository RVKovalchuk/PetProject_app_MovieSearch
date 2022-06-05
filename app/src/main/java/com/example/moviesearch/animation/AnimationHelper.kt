package com.example.moviesearch.animation

import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import java.util.concurrent.Executors
import kotlin.math.roundToInt

object AnimationHelper {
    private const val MENU_ITEMS_COUNT = 4
    private const val HALF_PARTS_OF_ITEM = 2
    private const val PREVIOUS_POSITION_OF_ITEM = 1
    private const val START_RADIUS_OF_CIRCULAR_REVEAL = 0
    private const val DURATION_OF_CIRCULAR_REVEAL : Long = 250


    //метод для создания круговой анимации появления фрагментов
    fun performFragmentCircularRevealAnimation(rootView: View, activity: Activity, position: Int) {
        Executors.newSingleThreadExecutor().execute {
            while (true) {
                if (rootView.isAttachedToWindow) {
                    activity.runOnUiThread {
                        val itemCenter = rootView.width / (MENU_ITEMS_COUNT * HALF_PARTS_OF_ITEM)
                        val step = (itemCenter * HALF_PARTS_OF_ITEM) * (position - PREVIOUS_POSITION_OF_ITEM) + itemCenter

                        val x: Int = step
                        val y: Int = rootView.y.roundToInt() + rootView.height

                        val startRadius = START_RADIUS_OF_CIRCULAR_REVEAL
                        val endRadius =
                            kotlin.math.hypot(rootView.width.toDouble(), rootView.height.toDouble())
                        ViewAnimationUtils.createCircularReveal(
                            rootView,
                            x,
                            y,
                            startRadius.toFloat(),
                            endRadius.toFloat()
                        ).apply {
                            duration = DURATION_OF_CIRCULAR_REVEAL
                            interpolator = AccelerateDecelerateInterpolator()
                            start()
                        }
                        rootView.visibility = View.VISIBLE
                    }
                    return@execute
                }
            }
        }
    }
}