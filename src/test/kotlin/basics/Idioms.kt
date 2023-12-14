package basics

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

/**
 * [Kotlin Idioms](https://kotlinlang.org/docs/idioms.html)
 */
class Idioms {
    @Test
    fun `create DTOs (POJOs POCOs)`() {
        data class Customer(val name: String, val email: String) {
            val greeting = "name: $name, email: $email"
        }

        val customer = Customer("foo", "foo@example.com")
        assertEquals("foo", customer.name)
        assertEquals("foo@example.com", customer.email)
        assertEquals("name: foo, email: foo@example.com", customer.greeting)
    }

    @Test
    fun `default values for function parameters`() {
        fun foo(a: Int = 0, b: String = "bar") {
            println("a is $a, b is $b")
        }

        foo(1)
    }

    @Test
    fun `filter a list`() {
        val list: List<Int> = listOf(-1, 2, -3, 4)

        val positives = list.filter { x -> x > 0 }
        assertEquals(2, positives[0])
        assertEquals(4, positives[1])

        val positives2 = list.filter { it > 0 }
        assertEquals(2, positives2[0])
        assertEquals(4, positives2[1])

        val positives3 = list.filter({ it > 0 })
        assertEquals(2, positives3[0])
        assertEquals(4, positives3[1])

        val numbers = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
        val filteredNumbers = numbers.filter { (key, value) -> key.endsWith("1") && value > 10 }
        assertEquals(11, filteredNumbers["key11"])
    }

    @Test
    fun `check the presence of an element in a collection`() {
        val list = listOf("foo", "bar", "baz")
        if ("foo" in list) {
            println("present")
        }
        if ("foo2" !in list) {
            println("absent")
        }
        assertTrue(list.contains("foo"))
    }

    @Test
    fun `string interpolation`() {
        val name = "lee"
        val age = 18
        assertEquals("spark lee, age 20", "spark $name, age ${age + 2}")
    }

    @Test
    fun `instance checks`() {
        fun describe(x: Any): String =
            when (x) {
                1 -> "one"
                "hello" -> "world"
                is Long -> "long"
                !is String -> "not a string"
                else -> "unknown"
            }
        assertEquals("one", describe(1))
        assertEquals("world", describe("hello"))
        assertEquals("long", describe(1L))
        assertEquals("not a string", describe(3.14))
        assertEquals("unknown", describe("foo"))
    }

    @Test
    fun `read-only list and map`() {
        val list = listOf("a", "b", "c")
        assertSame(3, list.size)
        assertSame(3, list.count())
        assertSame(1, list.count { it == "a" })

        val map = mapOf("a" to 1, "b" to 2, "c" to 3)
        assertSame(3, map.size)
        assertSame(3, map.count())
        assertSame(2, map.count { (_, v) -> v >= 2 })
    }

    @Test
    fun `access a map entry`() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 3)
        assertSame(1, map["a"])
    }

    @Test
    fun `traverse a map or a list of pairs`() {
        val list = listOf("a")
        for (item in list) {
            assertEquals("a", item)
        }

        val map = mapOf("spark" to 18)
        for ((name, age) in map) {
            assertEquals("spark", name)
            assertEquals(18, age)
        }
    }

    @Test
    fun `iterate over a range`() {
        var sum = 0

        // 闭区间，包含3
        for (i in 1..3) sum += i
        assertSame(6, sum)

        // 开区间，不包含3
        sum = 0
        for (i in 1..<3) sum += i
        assertSame(3, sum)

        // 指定步长
        sum = 0
        for (i in 2..6 step 2) sum += i
        assertSame(12, sum)

        // 递减
        sum = 0
        for (i in 3 downTo 1) sum += i
        assertSame(6, sum)

        // forEach
        sum = 0
        (1..3).forEach { i -> sum += i }
        assertSame(6, sum)
    }

    @Test
    fun `lazy property`() {
        val number = 10
        val p: String by lazy { "spark lee is age ${number + 18}" }
        assertEquals("spark lee is age 28", p)
    }

    @Test
    fun `extension functions`() {
        fun String.appendBar(): String {
            return "$this bar"
        }
        assertEquals("foo bar", "foo".appendBar())

        fun Int.multiplies(delta: Int): Int {
            return this * delta
        }
        assertSame(30, 3.multiplies(10))
    }

    @Test
    fun `create a singleton`() {
        assertEquals("user", UserRepository.modelName)
        assertEquals("model user", UserRepository.getModelNameDesc())
        assertSame(3, UserRepository.countByGender("male"))
    }
}

object UserRepository {
    val modelName = "user"

    fun countByGender(gender: String): Int {
        return 3
    }

    fun getModelNameDesc(): String {
        return "model $modelName"
    }
}