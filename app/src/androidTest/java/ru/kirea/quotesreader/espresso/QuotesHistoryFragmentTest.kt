package ru.kirea.quotesreader.espresso

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import ru.kirea.quotesreader.Consts
import ru.kirea.quotesreader.Ids
import ru.kirea.quotesreader.R
import ru.kirea.quotesreader.UiDeviceUtil
import ru.kirea.quotesreader.basic.quote.QuoteFragment
import ru.kirea.quotesreader.basic.quoteshistory.QuoteAdapter
import ru.kirea.quotesreader.basic.quoteshistory.QuotesHistoryFragment

class QuotesHistoryFragmentTest {
    private lateinit var scenario: FragmentScenario<QuoteFragment>
    private val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val uiDeviceUtil = UiDeviceUtil()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        //Запускаем Fragment в корне Activity
        scenario = FragmentScenario.launchInContainer(
            QuoteFragment::class.java,
            null,
            R.style.Theme_QuotesReader,
            null
        )

        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), Consts.TIMEOUT)
    }

    //тестирование списка цитат
    @Test
    fun testRecyclerViewItems() {
        //ожидаем загрузки первой цитаты
        uiDeviceUtil.waitProgressbarHide()

        //несколько раз обновляем цитаты
        val quoteList = updateQuote(count = 30)

        //открываем историю просмотра цитат и ожидаем появления списка
        FragmentScenario.launchInContainer(
            QuotesHistoryFragment::class.java,
            null,
            R.style.Theme_QuotesReader,
            null
        )

        uiDevice.wait(
            Until.findObject(By.res(packageName, Ids.QUOTES_RECYCLER_VIEW)),
            Consts.TIMEOUT
        )

        //проскролим до первой цитаты
        Espresso.onView(withId(R.id.quotes_recycler_view))
            .perform(
                RecyclerViewActions.scrollTo<QuoteAdapter.ViewHolder>(
                    hasDescendant(withText(quoteList[0]))
                )
            )
    }

    //обновить цитату указанное количество раз
    private fun updateQuote(count: Int): MutableList<String> {
        val result = mutableListOf<String>()

        val quoteText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, Ids.QUOTE_TEXT)),
                Consts.TIMEOUT
            )

        for (ind in 1..count) {
            uiDeviceUtil.fabUpdateQuoteClick()
            uiDeviceUtil.waitProgressbarHide()
            result.add(quoteText.text)
        }

        return result
    }
}