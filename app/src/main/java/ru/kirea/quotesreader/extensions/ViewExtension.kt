package ru.kirea.quotesreader.extensions

import android.animation.ValueAnimator
import android.view.View

//анимация для появления view
fun View.show() {
    //создаем анимацию изменения прозрачности
    ValueAnimator.ofFloat(0f, 1f)
        .apply {
            duration = 400
            addUpdateListener {
                this@show.alpha = this.animatedValue as Float
            }
        }
        .start()
}

//анимация для скрытия view
fun View.hide() {
    //создаем анимацию изменения прозрачности
    ValueAnimator.ofFloat(1f, 0f)
        .apply {
            duration = 400
            addUpdateListener {
                this@hide.alpha = this.animatedValue as Float
            }
        }
        .start()
}