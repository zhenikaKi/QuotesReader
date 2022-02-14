package ru.kirea.quotesreader

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

//вспмогательный класс для тестирования
class UiDeviceUtil {

    private val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    init {
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), Consts.TIMEOUT)
    }

    //ожидать скрытия прогрессбара
    fun waitProgressbarHide() {
        uiDevice.wait(
            Until.gone(By.res(packageName, Ids.PROGRESS_BAR)),
            Consts.TIMEOUT
        )
    }

    //нажать кнопку меню
    fun fabMenuClick() {
        val fabMenu = waitFabMenuVisible(Ids.FAB_MENU)
        fabMenu.click()
    }

    //нажать кнопку обновления цитаты
    fun fabUpdateQuoteClick() {
        fabMenuClick()
        waitFabMenuVisible(Ids.FAB_UPDATE_QUOTE).click()
    }

    //нажать кнопку настроек
    fun fabSettingClick() {
        fabMenuClick()
        waitFabMenuVisible(Ids.FAB_SETTING).click()
    }

    //нажать кнопку истории просмотра цитат
    fun fabHistoryClick() {
        fabMenuClick()
        waitFabMenuVisible(Ids.FAB_HISTORY_QUOTE).click()
    }

    //ожидать появления кнопки настроек
    private fun waitFabMenuVisible(id: String): UiObject2 {
        return uiDevice.wait(
            Until.findObject(By.res(packageName, id)),
            Consts.TIMEOUT
        )
    }
}