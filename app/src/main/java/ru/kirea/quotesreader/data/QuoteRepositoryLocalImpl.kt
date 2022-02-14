package ru.kirea.quotesreader.data

import io.reactivex.rxjava3.core.Single
import ru.kirea.quotesreader.data.entities.Quote
import java.util.*
import javax.inject.Inject

class QuoteRepositoryLocalImpl @Inject constructor(): QuoteRepository {

    companion object {
        private const val SYMBOLS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    }

    //Получить цитату с сайта
    override fun getQuote(): Single<Quote> {
        val random = Random()

        val quote = Quote(
            quoteId = System.currentTimeMillis(),
            quoteText = generateWords(countWord = random.nextInt(20) + 1),
            quoteAuthor = generateWord(length = random.nextInt(10))
        )

        return Single.just(quote)
    }

    //сгенерировать случайное предложение
    private fun generateWords(countWord: Int): String {
        val result = StringBuilder()
        for (ind in 1..countWord) {
            val word = generateWord(length = Random().nextInt(15))
            result.append(word).append(" ")
        }
        return result.toString()
    }

    //сгенерировать случайную строку
    private fun generateWord(length: Int): String {
        val result = StringBuilder()
        for (ind in 1..length) {
            var char = SYMBOLS[Random().nextInt(SYMBOLS.length)]
            if (Random().nextInt(2) % 2 == 0) {
                char = char.toLowerCase()
            }
            result.append(char)
        }
        return result.toString()
    }
}