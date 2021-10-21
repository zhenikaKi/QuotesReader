package ru.kirea.quotesreader.basic.setting

import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.kotlin.plusAssign
import ru.kirea.quotesreader.basic.BasePresenter
import ru.kirea.quotesreader.data.db.StorageDB
import ru.kirea.quotesreader.data.db.repositories.SettingDB
import ru.kirea.quotesreader.data.entities.Setting
import ru.kirea.quotesreader.data.events.BaseHandler
import ru.kirea.quotesreader.data.events.UpdatePeriodEvent
import ru.kirea.quotesreader.helpers.schedules.AppSchedulers

class SettingPresenter @AssistedInject constructor(
    router: Router,
    private val settingDB: SettingDB,
    private val schedulers: AppSchedulers,
    private val baseHandler: BaseHandler,
): BasePresenter<SettingView>(router) {

    override fun attachView(view: SettingView?) {
        super.attachView(view)
        viewState.init()
        //загружаем настройки
        loadData()
    }

    //сохранить новый период автообновления
    fun savePeriodUpdate(value: Int) {
        disposables += settingDB.save(Setting(StorageDB.PARAM_PERIOD_UPDATE, value = value.toString()))
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())
            .subscribe(
                //уведомляем слушателей о том, что изменился период автообновления
                {
                     baseHandler.send(UpdatePeriodEvent(value))
                },

                { exception ->
                    viewState.endLoadData()
                    viewState.showException(exception)
                }
            )
    }

    private fun loadData() {
        viewState.startLoadData()
        disposables += settingDB.getSettings()
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background()) //обработку делаем в отдельном потоке
            .subscribe(
                //уведомляем view о том, что получили цитату
                { settings ->
                    viewState.endLoadData()
                    viewState.showSettings(settings)
                },

                { exception ->
                    viewState.endLoadData()
                    viewState.showException(exception)
                }
            )
    }
}