package com.abdullrahman.weatherapp

import android.content.res.Resources
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

       // assertEquals(4, 2 + 2)
        println(getTimeOffset()+4.toLong())

    }
    fun getTimeOffset():Long{

        val calendar = Calendar.getInstance(
            TimeZone.getTimeZone("GMT"),
            Locale.getDefault()
        )
        val currentLocalTime = calendar.time
        val date: SimpleDateFormat = SimpleDateFormat("Z")
        val localTime: String = date.format(currentLocalTime)
        println(localTime)
        val l = localTime.subSequence(0,3)
       return l.toString().toLong()
    }
}