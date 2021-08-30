package ru.kirea.quotesreader.basic

interface BaseListPresenter<T> {

    //привязка элемента к view
    fun bindView(view: T)

    //количество элементов в списке
    fun getCount(): Int
}