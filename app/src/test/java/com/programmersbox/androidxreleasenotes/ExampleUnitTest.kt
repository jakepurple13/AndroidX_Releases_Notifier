package com.programmersbox.androidxreleasenotes

import org.junit.Test

import org.junit.Assert.*
import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val time = "2022-03-23T16:17:49.121008+00:00"

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

        println(format.parse(time)?.time)

    }
}