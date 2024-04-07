import org.geotools.api.feature.simple.SimpleFeature
import org.geotools.data.DataUtilities
import org.geotools.feature.simple.SimpleFeatureBuilder
import org.geotools.geometry.jts.GeometryBuilder
import org.locationtech.jts.geom.Point
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Tag
import java.util.concurrent.atomic.AtomicInteger

val BASE_ADDRESS_FEATURE_TYPE = DataUtilities.createType(
    "BaseAddress",
    "Street:String,Number:String,City:String,Location:Point,name:String"
)

val GEOMETRY_BUILDER = GeometryBuilder()

val ID_GENERATOR = AtomicInteger(0)

fun extractAddress(nodeContainer: NodeContainer?): SimpleFeature? {

    var street: String? = null
    var number: String? = null
    var cityName: String? = null
    var isSupermarket = false
    var supermarketBrandName: String? = null

    getTags(nodeContainer).forEach {
        when (it.key) {
            "addr:street" -> street = it.value
            "addr:housenumber" -> number = it.value
            "addr:city" -> cityName = it.value
            "shop" -> isSupermarket = it.value == "supermarket"
            "name" -> supermarketBrandName = it.value
        }
    }

    createAddressIfPossible(street, number, cityName)?.let {
        it.add(createPoint(nodeContainer))
        if (isSupermarket) {
            it.add(supermarketBrandName)
        }
        return it.buildFeature("${ID_GENERATOR.getAndIncrement()}")
    }

    return null
}

private fun createPoint(nodeContainer: NodeContainer?): Point {
    nodeContainer?.entity?.let {
        return GEOMETRY_BUILDER.point(it.longitude, it.latitude)
    }
    return GEOMETRY_BUILDER.point()
}

private fun getTags(entityContainer: EntityContainer?): Collection<Tag> {
    entityContainer?.entity?.tags?.run {
        return this
    }
    return mutableListOf()
}

private fun createAddressIfPossible(
    street: String?,
    number: String?,
    cityName: String?
): SimpleFeatureBuilder? {
    if (listOf(street, number, cityName).any { it.isNullOrBlank() }) {
        return null
    }
    val builder = SimpleFeatureBuilder(BASE_ADDRESS_FEATURE_TYPE)
    builder.add(street)
    builder.add(number)
    builder.add(cityName)
    return builder
}