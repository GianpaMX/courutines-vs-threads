package io.github.gianpamx.courutinesvsthreads

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.concurrent.thread

@RunWith(JUnit4::class)
class Callbacks {
    fun f(callback: (Int) -> Unit) {
        var x = 0
        thread {
            x = x + 1
            thread {
                x = x * 2
                callback(x)
            }
        }
    }

    @Test
    fun `Callback Hell`() {
        val firstCall = MyRetrofit.Call(1_000)

        firstCall.enqueueWithSuccess(object : MyRetrofit.Callback {
            override fun onResponse(firstResponse: MyRetrofit.Response) {
                val secondCall = MyRetrofit.Call(2_000)
                secondCall.enqueueWithNoSuccess(object : MyRetrofit.Callback {
                    override fun onResponse(secondResponse: MyRetrofit.Response) {
                        if (!secondResponse.isSuccessful) {
                            println("This was enqueued with no success")
                        }
                        println("Success: " + firstResponse.body + secondResponse.body)
                    }

                    override fun onFailure(e: Exception) {
                    }
                })
            }

            override fun onFailure(e: Exception) {
            }
        })

        Thread.sleep(3_100)
    }

    @Test
    fun `Coroutines`() = runBlocking {
        try {
            val firstResponse = MyRetrofit.Call(1_000).adapt().await()
            val secondResponse = MyRetrofit.Call(1_000).adapt().await()
            println("Success: " + firstResponse.body + secondResponse.body)
        } catch (e: Exception) {
            println("This was enqueued with no success")
        }
    }
}
