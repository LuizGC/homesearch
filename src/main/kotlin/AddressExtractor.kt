import org.geotools.api.feature.simple.SimpleFeature
import org.geotools.data.DataUtilities
import org.geotools.feature.simple.SimpleFeatureBuilder
import org.geotools.geometry.jts.GeometryBuilder
import org.locationtech.jts.geom.Point
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer
import org.openstreetmap.osmosis.core.container.v0_6.WayContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Node
import org.openstreetmap.osmosis.core.domain.v0_6.Tag
import java.util.concurrent.atomic.AtomicInteger

val BASE_ADDRESS_FEATURE_TYPE = DataUtilities.createType(
    "BaseAddress",
    "street:String,number:String,city:String,name:String,location:Point"
)

val GEOMETRY_BUILDER = GeometryBuilder()

val ID_GENERATOR = AtomicInteger(0)

val NODES = mutableMapOf<Long, Node>()

fun extractAddress(wayContainer: WayContainer?): SimpleFeature? {
    return processEntity(wayContainer)?.let {
        it.set("location", createPoint(wayContainer))
        it.buildFeature("${ID_GENERATOR.getAndIncrement()}")
    }
}

fun extractAddress(nodeContainer: NodeContainer?): SimpleFeature? {
    nodeContainer?.entity?.let {
        NODES.put(it.id, it)
    }

    return processEntity(nodeContainer)?.let {
        it.set("location", createPoint(nodeContainer))
        it.buildFeature("${ID_GENERATOR.getAndIncrement()}")
    }}

private fun processEntity(entityContainer: EntityContainer?): SimpleFeatureBuilder? {
    var street: String? = null
    var number: String? = null
    var cityName: String? = null
    var isSupermarket = false
    var supermarketBrandName: String? = null

    getTags(entityContainer).forEach {
        when (it.key) {
            "addr:street" -> street = it.value
            "addr:housenumber" -> number = it.value
            "addr:city" -> cityName = it.value
            "shop" -> isSupermarket = it.value == "supermarket"
            "name" -> supermarketBrandName = it.value
        }
    }

    createAddressIfPossible(street, number, cityName)?.let {
        if (isSupermarket) {
            it.set("name", supermarketBrandName)
        }
        return it
    }
    return null
}

private fun createPoint(nodeContainer: NodeContainer?): Point {
    nodeContainer?.entity?.let {
        return GEOMETRY_BUILDER.point(it.longitude, it.latitude)
    }
    return GEOMETRY_BUILDER.point()
}

private fun createPoint(wayContainer: WayContainer?): Point {
    wayContainer?.entity?.wayNodes?.first {
        val node = NODES.get(it.nodeId)
        return GEOMETRY_BUILDER.point(node?.longitude?:0.0, node?.latitude?:0.0)

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
    builder.set("street", street)
    builder.set("number", number)
    builder.set("city", cityName)
    return builder
}