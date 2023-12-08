package basics

import org.junit.jupiter.api.Test
import kotlin.test.assertSame

/**
 * 参考 [Kotlin Basic syntax](https://kotlinlang.org/docs/basic-syntax.html)
 */
class BasicSyntaxTest {
    @Test
    fun `print to the standard output`() {
        print("Hello, ")
        println("world!")
    }

    @Test
    fun functions() {
        fun sum(a: Int, b: Int): Int {
            return a + b
        }
        assertSame(3, sum(1, 2))

        fun sum2(a: Int, b: Int) = a + b
        assertSame(3, sum2(1, 2))

        fun printSum(a: Int, b: Int): Unit {
            println("sum of $a and $b is ${a + b}")
        }
        printSum(1, 2)
    }
}