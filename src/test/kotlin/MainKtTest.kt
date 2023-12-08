import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.datastax.oss.driver.api.querybuilder.select.Select
import com.datastax.oss.driver.api.querybuilder.select.Selector
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

class MainKtTest {

    private val session: CqlSession = CqlSession.builder()
//        .withAuthCredentials("cassandra", "cassandra")
        .withAuthCredentials("admin", "111111")
        .withKeyspace("system")
        .build()

    @Test
    fun one() {
        val rs: ResultSet = session.execute("select release_version from system.local")
        val row: Row? = rs.one();
        assertNotNull(row)
        assertEquals("4.1.3", row.getString("release_version"))
    }

    @Test
    fun countAll() {
        val select: Select = QueryBuilder
            .selectFrom("system_schema", "columns")
            .countAll()
        val statement: SimpleStatement = select.build()
        val rs: ResultSet = session.execute(statement)
        val row: Row? = rs.one()
        assertNotNull(row)
        assert(row.getLong(0) > 0)
    }

    @Test
    fun name() {
        val session: CqlSession = CqlSession
            .builder()
            .withAuthCredentials("admin", "111111")
            .withKeyspace("sdk")
            .build()

        session.execute("truncate orders")

        session.execute(
            "insert into orders(order_no,user_id) values(" + java.util.UUID.randomUUID().toString() + ", 666)"
        )

        val select: Select =
//             你是搞笑么？
            QueryBuilder.selectFrom("sdk", "orders")
                .column("order_no").`as`("orderno")
                .column("user_id")
//                .selector(Selector.column("order_no"))
//                .selectors(
//                    Selector.column("order_no"),
//                    Selector.column("user_id"),
//                )
                .whereColumn("user_id").isEqualTo(QueryBuilder.bindMarker())
                .allowFiltering()
                .limit(1)
        val statement: SimpleStatement = select.builder().addPositionalValue(666L).build()
        val rs: ResultSet = session.execute(statement)
        val row = rs.one()
        assertSame(36, row?.getUuid("orderno").toString().length)

        assertEquals(
            "SELECT * FROM sdk.orders",
            QueryBuilder.selectFrom("sdk", "orders").all().asCql()
        )
        assertEquals(
            "SELECT * FROM sdk.orders",
            QueryBuilder.selectFrom("sdk", "orders").column("order_no").all().asCql()
        )
        assertEquals(
            "SELECT user_id FROM orders",
            QueryBuilder.selectFrom("orders").all().column("user_id").asCql()
        )
        assertEquals(
            "SELECT count(*) FROM orders",
            QueryBuilder.selectFrom("orders").countAll().asCql()
        )
        assertEquals(
            "SELECT order_no,user_id FROM orders",
            QueryBuilder.selectFrom("orders").columns("order_no", "user_id").asCql()
        )
        QueryBuilder.selectFrom("rooms").multiply(Selector.column("length"), Selector.column("width"))
            .`as`("surface").asCql()
        assertEquals(
            "SELECT length+width AS surface FROM rooms",
            QueryBuilder.selectFrom("rooms").add(Selector.column("length"), Selector.column("width"))
                .`as`("surface").asCql()
        )
        assertEquals(
            "SELECT length-width AS surface FROM rooms",
            QueryBuilder.selectFrom("rooms").subtract(Selector.column("length"), Selector.column("width"))
                .`as`("surface").asCql()
        )
        assertEquals(
            "SELECT length*width AS surface FROM rooms",
            QueryBuilder.selectFrom("rooms").multiply(Selector.column("length"), Selector.column("width"))
                .`as`("surface").asCql()
        )
        assertEquals(
            "SELECT length/width AS surface FROM rooms",
            QueryBuilder.selectFrom("rooms").divide(Selector.column("length"), Selector.column("width"))
                .`as`("surface").asCql()
        )
        assertEquals(
            "SELECT length%width AS surface FROM rooms",
            QueryBuilder.selectFrom("rooms").remainder(Selector.column("length"), Selector.column("width"))
                .`as`("surface").asCql()
        )
        assertEquals(
            "SELECT -length AS surface FROM rooms",
            QueryBuilder.selectFrom("rooms").negate(Selector.column("length"))
                .`as`("surface").asCql()
        )
        assertEquals(
            "SELECT -length AS surface FROM rooms",
            QueryBuilder.selectFrom("rooms").selector(Selector.negate(Selector.column("length")))
                .`as`("surface").asCql()
        )
        assertEquals(
            "SELECT -a*(b+c) FROM foo",
            QueryBuilder.selectFrom("foo").multiply(
                Selector.negate(Selector.column("a")),
                Selector.add(Selector.column("b"), Selector.column("c"))
            ).asCql()
        )
        assertEquals(
            "SELECT -a*(b+c) FROM foo",
            QueryBuilder.selectFrom("foo").selector(
                Selector.multiply(
                    Selector.negate(Selector.column("a")),
                    Selector.add(Selector.column("b"), Selector.column("c"))
                )
            ).asCql()

        )
    }
}