package ru.kirea.quotesreader.espresso

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.FragmentScenario.Companion.launchInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.kirea.quotesreader.*
import ru.kirea.quotesreader.basic.quote.QuoteFragment

@RunWith(AndroidJUnit4::class)
class QuoteFragmentTest {
    private lateinit var scenario: FragmentScenario<QuoteFragment>
    private val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val uiDeviceUtil = UiDeviceUtil()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        //Запускаем Fragment в корне Activity
        scenario = launchInContainer(QuoteFragment::class.java, null, R.style.Theme_QuotesReader, null)

        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), Consts.TIMEOUT)
    }

    //проверить наличие индикатора загрузки цитаты
    @Test
    fun testProgressBarVisibleOnStartFragment() {
        Espresso.onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
    }

    //проверить появление плаваюих кнопок при нажатии на основную кнопку
    @Test
    fun testFloatingActionButtonsVisible() {
        //нажимаем кнопку меню
        uiDeviceUtil.fabMenuClick()

        //проверяем, что отобразились кнопки меню
        Espresso.onView(withId(R.id.fab_update_quote)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.fab_history_quote)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.fab_setting)).check(matches(isDisplayed()))
    }

    //проверить ручную смену цитаты
    @Test
    fun testUpdateQuote() {
        //ожидаем появления цитаты
        val quoteText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, Ids.QUOTE_TEXT)),
                Consts.TIMEOUT
            )
        val oldText = quoteText.text

        //обновляем цитату
        uiDeviceUtil.fabUpdateQuoteClick()

        //проверяем, что показывается новая цитата
        uiDeviceUtil.waitProgressbarHide()
        val newText = quoteText.text
        Assert.assertNotEquals(oldText, newText)
    }

    //проверить настройку интервала автообновления
    @Test
    fun testChangePeriodAutoUpdate() {
        //ожидаем загрузки первой цитаты
        uiDeviceUtil.waitProgressbarHide()
        val reverseTimerText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, Ids.REVERSE_TIMER_TEXT)),
                Consts.TIMEOUT
            )

        for (update in 25..30 step 5) {
            //открываем меню и нажимаем кнопку настроек
            uiDeviceUtil.fabSettingClick()

            //ожидаем появление бегунка с настройкой автообновления
            uiDevice.wait(
                Until.findObject(By.res(packageName, Ids.PERIOD_UPDATE_VALUE)),
                Consts.TIMEOUT
            )

            //меняем параметры и проверяем
            Espresso.onView(withId(R.id.period_update_value)).perform(SliderUtils.setValue(update.toFloat()))
            closeSettingAndUpdateQuote()
            reverseTimerText.wait(
                Until.textEquals("до обновления: ${update-1} сек"),
                Consts.TIMEOUT
            )
        }
    }

    //закрыть окна с настройкой, обновить цитату
    private fun closeSettingAndUpdateQuote() {
        uiDevice.pressBack()
        uiDeviceUtil.fabUpdateQuoteClick()
    }
}