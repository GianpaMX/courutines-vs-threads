package io.github.gianpamx.courutinesvsthreads

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.concurrent.thread

@RunWith(JUnit4::class)
class CoroutinesBasics {

    private fun getThreadName() = Thread.currentThread().name

    @Test
    fun `Threads`() {
        thread {
            Thread.sleep(100L)
            println("inner name: ${getThreadName()}")
        }

        println("outer name: ${getThreadName()}")
        Thread.sleep(200L)
    }

    @Test
    fun `Coroutines`() {
        GlobalScope.launch {
            delay(100L)
            println("inner name: ${getThreadName()}")
        }

        println("outer name: ${getThreadName()}")
        runBlocking {
            delay(200L)
        }
    }

    @Test
    fun `Launch in the same thread`() {
        runBlocking {
            launch(coroutineContext) {
                delay(100L)
                println("inner name: ${getThreadName()}")
            }

            println("outer name: ${getThreadName()}")
            delay(200L)
        }
    }

    @Test
    fun `Sleep vs Delay`() = runBlocking {
        launch(coroutineContext) {
            println("In start : ${getThreadName()}")
            Thread.sleep(200L)
//            delay(200L)
            println("In ended : ${getThreadName()}")
        }

        run {
            println("Out start: ${getThreadName()}")
            Thread.sleep(300L)
//            delay(300L)
            println("Out ended: ${getThreadName()}")
        }
    }

    @Test
    fun `100k Threads`() {
        for (i in 0..100_000) {
            thread {
                Thread.sleep(1_000L)
                print(".")
            }
        }
    }

    @Test
    fun `100k Coroutines`() {
        runBlocking {
            for (i in 0..10_000) {
                launch(this.coroutineContext) {
                    delay(1_000L)
                    println(".")
                }
            }
        }
    }

    @Test
    fun `What 100k coroutines actually do`() {
        Thread.sleep(1_000L)
        for (i in 0..100_000) {
            print(".")
        }
    }
}
