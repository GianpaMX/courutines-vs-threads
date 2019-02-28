package io.github.gianpamx.courutinesvsthreads

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.concurrent.thread

@RunWith(JUnit4::class)
class Threads {
    @Test
    fun `Parallelism`() {
        fun f(): Int {
            var x = 0
            thread { x = x + 1 }
            thread { x = x * 2 }
            return x
        }

        assert(f() == 1) // 0, 1 or 2
    }
}
