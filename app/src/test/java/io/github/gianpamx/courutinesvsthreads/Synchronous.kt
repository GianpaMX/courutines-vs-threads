package io.github.gianpamx.courutinesvsthreads

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class Synchronous {
    @Test
    fun `Synchronous Call`() {
        val firstCall = MyRetrofit.Call(1_000)
        val firstResponse = firstCall.execute()
        if(firstResponse.isSuccessful) {
            val secondCall = MyRetrofit.Call(1_000)
            secondCall.execute()
        } else {
            throw Exception("Someone else will deal with this")
        }
    }
}
