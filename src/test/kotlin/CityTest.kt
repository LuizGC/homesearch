import org.example.City
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.openstreetmap.osmosis.core.domain.v0_6.CommonEntityData
import org.openstreetmap.osmosis.core.domain.v0_6.OsmUser
import org.openstreetmap.osmosis.core.domain.v0_6.Tag
import org.openstreetmap.osmosis.core.domain.v0_6.Way
import java.util.*
import kotlin.test.Test
import kotlin.test.assertFalse

class CityTest {

    private fun createEntity(cityName: String?): Way {
        val tags: List<Tag> = cityName?.let {
            listOf(Tag("addr:city", it))
        } ?: listOf()
        val commonEntityData = createCommonEntityData(tags)
        return Way(commonEntityData)
    }

    private fun createCommonEntityData(tags: List<Tag>) =
        CommonEntityData(1, 1, Date(), OsmUser(1, ""), 1, tags)


    private val gdyniaEntity = createEntity("Gdynia")
    private val emptyCityEntity = createEntity(null)

    @TestFactory
    fun `given OSM entities in Gdynia when`() = listOf(
        "Gdynia" to true,
        "Gdansk" to false,
        "GDASNK" to false,
        "GdYnia" to true,
        "GDYNIA" to true
    ).map { (cityName, expected) ->
        dynamicTest("$cityName then return $expected") {
            val city = City(cityName)
            assertEquals(expected, city.has(gdyniaEntity))
        }
    }

    @Test
    fun `given OSM entities with empty city when check the city name should throw exception`() {
        val city = City("Gdynia")
        assertFalse(city.has(emptyCityEntity))
    }

}