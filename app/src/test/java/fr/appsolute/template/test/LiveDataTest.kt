package fr.appsolute.template.test

import androidx.lifecycle.LiveData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getBlockingValue(timeOut: Int = 2): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    observeForever {
        value = it
        latch.countDown()
    }
    if (!latch.await(timeOut.toLong(), TimeUnit.SECONDS)) {
        throw TimeoutException("LiveData value was never set.")
    }
    return value
}
