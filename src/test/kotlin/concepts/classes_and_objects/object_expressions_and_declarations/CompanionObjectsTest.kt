package concepts.classes_and_objects.object_expressions_and_declarations

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

/**
 * [Companion objects](https://kotlinlang.org/docs/object-declarations.html#companion-objects)
 */
class CompanionObjectsTest {
    @Test
    fun `members of the companion object can be called simply by using the class name as the qualifier`() {
        val instance = MyClass.create()
        assertEquals("foo", instance.name)
    }

    @Test
    fun `class members can access the private members of the corresponding companion object`() {
        val instance = MyClass.Factory.create()
        assertSame(18, instance.getAge())
    }

    @Test
    fun `the name of a class used by itself acts as a reference to the companion object of the class`() {
        val x = MyClass
        assertSame(18, x.create().getAge())

        val y = MyClass.Factory
        assertSame(18, y.create().getAge())
    }
}

class MyClass {
    val name: String = "foo"

    companion object Factory {
        private val age = 18;

        fun create(): MyClass = MyClass()
    }

    fun getAge(): Int {
        return age
    }
}