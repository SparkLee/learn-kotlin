import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.QueryBuilder

data class User(val id: Int, val name: String) {
    override fun toString(): String {
        return "User(id=$id, name='$name')"
    }
}

fun main() {
    val user = User(1, "foo")
    val map: Map<String, Int> = mapOf("aaa" to 1, "bbb" to 2, "ccc" to 3)
}