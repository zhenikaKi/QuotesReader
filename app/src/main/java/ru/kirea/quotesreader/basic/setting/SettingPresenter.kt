package ru.kirea.quotesreader.basic.setting

import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.kotlin.plusAssign
import ru.kirea.quotesreader.basic.BasePresenter
import ru.kirea.quotesreader.data.db.StorageDB
import ru.kirea.quotesreader.data.db.repositories.SettingDB
import ru.kirea.quotesreader.data.entities.Setting
import ru.kirea.quotesreader.helpers.schedules.AppSchedulers

class SettingPresenter @AssistedInject constructor(
    router: Router,
    private val settingDB: SettingDB,
    private val schedulers: AppSchedulers
): BasePresenter<SettingView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //загружаем цитату
        loadData()
    }

    //сохранить новый период автообновления
    fun savePeriodUpdate(value: Int) {
        //TODO сохранить новое значение
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