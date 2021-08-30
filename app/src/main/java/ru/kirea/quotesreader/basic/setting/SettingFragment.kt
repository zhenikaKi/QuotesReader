package ru.kirea.quotesreader.basic.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.ktx.moxyPresenter
import ru.kirea.quotesreader.R
import ru.kirea.quotesreader.basic.listeners.BackButtonListener
import ru.kirea.quotesreader.data.db.StorageDB
import ru.kirea.quotesreader.data.entities.Setting
import ru.kirea.quotesreader.databinding.SettingFragmentBinding
import ru.kirea.quotesreader.extensions.showToast
import ru.kirea.quotesreader.helpers.di.BaseDaggerBottomSheetFragment
import javax.inject.Inject

class SettingFragment: BaseDaggerBottomSheetFragment(), SettingView, BackButtonListener {

    @Inject
    lateinit var settingPresenterFactory: SettingPresenterFactory

    companion object {
        private const val TAG = "SettingFragment"

        fun show(fragmentManager: FragmentManager) {
            SettingFragment().show(fragmentManager, TAG)
        }
    }

    private val binding: SettingFragmentBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val presenter by moxyPresenter { settingPresenterFactory.create() }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            binding.root

    override fun backPressed() = presenter.backPressed()

    override fun init() {
        buttonListener()

        //укажем период по умолчанию
        binding.periodUpdateValue.value = StorageDB.PERIOD_UPDATE_DEFAULT.toFloat()
    }

    //запустить процесс получения настроек
    override fun startLoadData() {
        binding.progressBar.visibility = View.VISIBLE
        binding.settingData.visibility = View.GONE
    }

    //завершить процесс получения настроек
    override fun endLoadData() {
        binding.progressBar.visibility = View.GONE
        binding.settingData.visibility = View.VISIBLE
    }

    //обработать полученные настройки
    override fun showSettings(settings: List<Setting>) {
        settings.forEach { setting ->
            when(setting.settingName) {
                StorageDB.PARAM_PERIOD_UPDATE -> binding.periodUpdateValue.value = setting.value.toFloat()
            }
        }
    }

    //показать возникшую ошибку
    override fun showException(throwable: Throwable) {
        requireContext().showToast(throwable.message)
    }

    //обработать кнопки
    private fun buttonListener() {
        //выбор периода обновления цитаты
        binding.periodUpdateValue.addOnChangeListener{ _, value, _ ->
            val title = String.format(getString(R.string.period_update_text), value.toInt())
            binding.periodUpdateText.text = title
            presenter.savePeriodUpdate(value.toInt())
        }
    }
}