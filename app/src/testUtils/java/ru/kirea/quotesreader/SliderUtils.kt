package ru.kirea.quotesreader

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.slider.Slider
import org.hamcrest.Matcher

object SliderUtils {
    //установить значение для бегунка
    fun setValue(value: Float): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "$value"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(Slider::class.java)
            }

            override fun perform(uiController: UiController?, view: View) {
                val seekBar = view as Slider
                seekBar.value = value
            }
        }
    }
}