package concepts.classes_and_objects.object_expressions_and_declarations

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [Object expressions](https://kotlinlang.org/docs/object-declarations.html#object-expressions)
 */
class AnonymousObjectsTest {
    @Test
    fun `creating anonymous objects from scratch`() {
        val helloWorld = object {
            val hello = "Hello"
            val world = "World"

            // object expressions extend Any, so `override` is required on `toString()`
            override fun toString() = "$hello $world"

            fun greeting(): String {
                return "hi";
            }
        }

        assertEquals("Hello", helloWorld.hello)
        assertEquals("World", helloWorld.world)
        assertEquals("Hello World", helloWorld.toString())
        assertEquals("hi", helloWorld.greeting())
    }

    @Test
    fun `inheriting anonymous objects from supertypes`() {


    }
}

open class A(x: Int) {
    public open val y: Int = x
}