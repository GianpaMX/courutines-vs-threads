package io.github.gianpamx.courutinesvsthreads

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class Coroutines {
    suspend fun f(): Int = runBlocking {
        var x = 0
        x = async { x + 1 }.await()
        x = async { x * 2 }.await()
        return@runBlocking x
    }

    @Test
    fun `Same example with coroutines`() = runBlocking {
        assert(f() == 2)
    }
}
