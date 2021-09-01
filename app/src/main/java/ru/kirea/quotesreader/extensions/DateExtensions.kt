package ru.kirea.quotesreader.extensions

import java.text.SimpleDateFormat
import java.util.*

//вывести дату в нужном виде
fun Date.format(pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(this)
}