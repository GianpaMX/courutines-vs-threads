package io.github.gianpamx.courutinesvsthreads

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class Callbacks {
    @Test
    fun `Callback Hell`() {
        val firstCall = MyRetrofit.Call(1_000)

        firstCall.enqueueWithSuccess(object : MyRetrofit.Callback {
            override fun onResponse(response: MyRetrofit.Response) {
                val secondCall = MyRetrofit.Call(2_000)
                secondCall.enqueueWithNoSuccess(object : MyRetrofit.Callback {
                    override fun onResponse(response: MyRetrofit.Response) {
                        if (!response.isSuccessful) {
                            println("This was enqueued with no success")
                        }
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
}
