package concepts.classes_and_objects.classes

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [Classes](https://kotlinlang.org/docs/classes.html)
 */
class ClassesTest {
    @Test
    fun `primary constructor`() {
        /**
         * 演示类的初始化顺序
         */
        class InitOrderDemo(name: String) {
            val firstProperty = "First property: $name".also(::println)

            init {
                println("First initializer block that prints $name")
            }

            val secondProperty = "Second property: ${name.length}".also(::println)

            init {
                println("Second initializer block that prints ${name.length}")
            }

            private val uppercaseName = name.uppercase()

            fun getName(): String {
                return uppercaseName;
            }
        }

        val initOrderDemo = InitOrderDemo("hello")
        assertEquals("HELLO", initOrderDemo.getName())
    }

    @Test
    fun `declaring properties and initializing them from the primary constructor`() {
        class Person (
            private val firstName: String,
            val lastName: String,

            // Trailing commas（最后一个参数的末尾可以带逗号）: https://kotlinlang.org/docs/coding-conventions.html#trailing-commas
            val age: Int = 18,
        ) {
            fun getFirstName(): String {
                return firstName
            }
        }

        val person = Person("spark", "lee")
        assertEquals("spark", person.getFirstName())
        assertEquals("lee", person.lastName)
        assertEquals(18, person.age)
    }

    @Test
    fun `secondary constructors`() {

    }
}
