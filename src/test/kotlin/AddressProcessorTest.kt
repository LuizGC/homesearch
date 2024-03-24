import org.example.City
import org.junit.jupiter.api.BeforeEach
import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer
import org.openstreetmap.osmosis.core.container.v0_6.RelationContainer
import org.openstreetmap.osmosis.core.container.v0_6.WayContainer
import org.openstreetmap.osmosis.core.domain.v0_6.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AddressProcessorTest {

    private val validAddressTagList = listOf(
        Tag("addr:city", "Gdynia"),
        Tag("addr:street", "Chwarznieńska"),
        Tag("addr:housenumber", "178A")
    )
    private val emptyTagList = listOf<Tag>()

    private val addresses = mutableSetOf<Address>()
    private val addressProcessor = AddressProcessor(addresses)

    @BeforeEach
    fun setup() {
        addresses.clear()
    }

    @Test
    fun `given OSM nodes with same address should return set with one address`() {
        // given
        val nodeOne = Node(createEntityData(validAddressTagList), 0.0, 0.0)
        val nodeTwo = Node(createEntityData(validAddressTagList), 0.0, 0.0)

        // when
        addressProcessor.process(NodeContainer(nodeOne))
        addressProcessor.process(NodeContainer(nodeTwo))

        // then
        assertEquals(1, addresses.size)
        assertContains(addresses, Address("Chwarznieńska", "178A", City("Gdynia")))
    }

    @Test
    fun `given OSM way with same address should return set with one address`() {
        // given
        val wayOne = Way(createEntityData(validAddressTagList))
        val wayTwo = Way(createEntityData(validAddressTagList))

        // when
        addressProcessor.process(WayContainer(wayOne))
        addressProcessor.process(WayContainer(wayTwo))

        // then
        assertEquals(1, addresses.size)
        assertContains(addresses, Address("Chwarznieńska", "178A", City("Gdynia")))
    }

    @Test
    fun `given OSM relation with same address should return set with one address`() {
        // given
        val relationOne = Relation(createEntityData(validAddressTagList))
        val relationTwo = Relation(createEntityData(validAddressTagList))

        // when
        addressProcessor.process(RelationContainer(relationOne))
        addressProcessor.process(RelationContainer(relationTwo))

        // then
        assertEquals(1, addresses.size)
        assertContains(addresses, Address("Chwarznieńska", "178A", City("Gdynia")))
    }

    @Test
    fun `given OSM nodes, way and relation with same address should return set with one address`() {
        // given
        val node = Node(createEntityData(validAddressTagList), 0.0, 0.0)
        val way = Way(createEntityData(validAddressTagList))
        val relation = Relation(createEntityData(validAddressTagList))

        // when
        addressProcessor.process(NodeContainer(node))
        addressProcessor.process(WayContainer(way))
        addressProcessor.process(RelationContainer(relation))

        // then
        assertEquals(1, addresses.size)
        assertContains(addresses, Address("Chwarznieńska", "178A", City("Gdynia")))
    }

    @Test
    fun `given OSM nodes, way and relation without address should return empty list`() {
        // given
        val node = Node(createEntityData(emptyTagList), 0.0, 0.0)
        val way = Way(createEntityData(emptyTagList))
        val relation = Relation(createEntityData(emptyTagList))

        // when
        addressProcessor.process(NodeContainer(node))
        addressProcessor.process(WayContainer(way))
        addressProcessor.process(RelationContainer(relation))

        // then
        assert(addresses.isEmpty())
    }

    private fun createEntityData(tags: List<Tag>): CommonEntityData =
        CommonEntityData(1, 1, Date(), OsmUser(1, ""), 1, tags)
}