package ru.kirea.quotesreader.helpers.schedules

import io.reactivex.rxjava3.core.Scheduler

interface AppSchedulers {

    fun background(): Scheduler
    fun main(): Scheduler
}