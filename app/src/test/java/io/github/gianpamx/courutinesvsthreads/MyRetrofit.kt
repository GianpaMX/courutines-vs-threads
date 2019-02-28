package io.github.gianpamx.courutinesvsthreads

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlin.concurrent.thread

class MyRetrofit {
    data class Response(
        val body: String = "",
        val isSuccessful: Boolean = true
    )


    interface Callback {
        fun onResponse(response: Response)
        fun onFailure(e: Exception)
    }

    class Call(private val millis: Long) {
        fun enqueueWithSuccess(callback: Callback) {
            thread {
                Thread.sleep(millis)
                callback.onResponse(Response(body = "Hello world"))
            }
        }

        fun enqueueWithNoSuccess(callback: Callback) {
            thread {
                Thread.sleep(millis)
                callback.onResponse(Response(isSuccessful = false))
            }
        }

        fun enqueueWithFailure(callback: Callback) {
            thread {
                Thread.sleep(millis)
                callback.onFailure(RuntimeException("Enqueue with failure"))
            }
        }

        fun execute(): Response {
            return Response(body = "Hello world")
        }

        fun adapt(): Deferred<Response> {
            val deferred = CompletableDeferred<Response>()
            enqueueWithSuccess(object: Callback {
                override fun onResponse(response: Response) {
                    if(response.isSuccessful) {
                        deferred.complete(response)
                    } else {
                        deferred.completeExceptionally(Exception("Failure"))
                    }

                }

                override fun onFailure(e: Exception) {
                    deferred.completeExceptionally(e)
                }
            })
            return deferred
        }
    }
}
