package concepts.classes_and_objects.interfaces

import jnr.posix.POSIX
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * https://kotlinlang.org/docs/interfaces.html
 */
class InterfacesTest {
    interface MyInterface {
        // declare abstract method
        fun foo(): String

        // method implementation
        fun bar(): String {
            return "bar"
        }
    }

    interface MyAnotherInterface {
        fun baz(): String
    }

    @Test
    fun `defining and implementing interfaces`() {
        class Child : MyInterface, MyAnotherInterface {
            override fun foo(): String {
                return "foo"
            }

            override fun baz(): String {
                return "baz"
            }
        }

        val child = Child()
        assertEquals("foo", child.foo())
        assertEquals("bar", child.bar())
        assertEquals("baz", child.baz())
    }

    interface MyInterfaceWithProperties {
        // abstract property
        val prop: Int

        // provide implementation for accessor
        val propertyWithImplementation: String
            get() = "foo"

        fun foo(): Int {
            return prop
        }
    }


    @Test
    fun `properties in interfaces`() {
        // abstract property must be implemented
        class Child(override val prop: Int = 18) : MyInterfaceWithProperties {
        }

        val child = Child()
        assertEquals(18, child.prop)
        assertEquals("foo", child.propertyWithImplementation)
    }

    interface Named {
        val name: String
    }

    interface Person : Named {
        val firstName: String
        val lastName: String

        override val name: String get() = "$firstName $lastName"
    }

    @Test
    fun `interfaces inheritance`() {
        data class Employee(
            // implementing 'name' is not required
            override val firstName: String,
            override val lastName: String,
            val position: String,
        ) : Person

        val employee = Employee("spark", "lee", "tech leader")
        assertEquals("spark lee", employee.name)

        val (firstName, lastName, position) = Employee("spark", "lee", "tech leader")
        assertEquals("spark", firstName)
        assertEquals("lee", lastName)
        assertEquals("tech leader", position)
    }

    interface A {
        fun foo(): String {
            return "A foo"
        }

        fun bar(): String
    }

    interface B {
        fun foo(): String {
            return "B foo"
        }

        fun bar(): String {
            return "B bar"
        }
    }

    @Test
    fun `resolving overriding conflicts`() {
        class C : A {
            override fun bar(): String {
                return "C bar"
            }
        }
        assertEquals("C bar", C().bar())

        class D : A, B {
            override fun foo(): String {
                return super<A>.foo() + "|" + super<B>.foo()
            }

            override fun bar(): String {
                return super<B>.bar()
            }
        }

        val d = D()
        assertEquals("A foo|B foo", d.foo())
        assertEquals("B bar", d.bar())
    }
}
