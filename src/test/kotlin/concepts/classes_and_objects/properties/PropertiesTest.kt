package concepts.classes_and_objects.properties

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

class PropertiesTest {
    @Test
    fun `custom accessors (getter and setter)`() {
        class Rectangle(val width: Int, val height: Int) {
            val area: Int // property type is optional since it can be inferred from the getter's return type
                get() = this.width * this.height

            val area2 get() = this.area

            var area3: Int = 0 // the initializer assigns the backing field directly
                get() = field * 10
                set(value) {
                    field = value * 10
                }
        }

        val rectangle = Rectangle(3, 4)

        assertEquals(
            "Width=3, height=4, area=12",
            "Width=${rectangle.width}, height=${rectangle.height}, area=${rectangle.area}"
        )
        assertSame(rectangle.area, rectangle.area2)

        assertEquals(0, rectangle.area3)
        rectangle.area3 = 2
        assertEquals(200, rectangle.area3)
    }

    @Test
    fun `late-initialized properties and variables`() {
        class Citizen(val name: String) {
            lateinit var neighbor: Citizen

            fun getNeighborName(): String {
                return "dear " + neighbor.name // dereference directly
            }

            /**
             * To check whether a lateinit var has already been initialized, use .isInitialized on the reference to that property
             */
            fun isNeighborAssigned(): Boolean {
                return this::neighbor.isInitialized
            }
        }

        val foo = Citizen("john")

        assertFalse(foo.isNeighborAssigned())
        foo.neighbor = Citizen("lily")
        assertTrue(foo.isNeighborAssigned())

        assertEquals("dear lily", foo.getNeighborName())
    }
}