import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Tag

fun extractAddress(entityContainer: EntityContainer?): Address? {
    var street: String? = null
    var number: String? = null
    var cityName: String? = null
    var supermarket = false
    var supermarketBrandName: String? = null
    getTags(entityContainer).forEach {
        when (it.key) {
            "addr:street" -> street = it.value
            "addr:housenumber" -> number = it.value
            "addr:city" -> cityName = it.value
            "shop" -> supermarket = it.value == "supermarket"
            "name" -> supermarketBrandName = it.value
        }
    }
    createAddressIfNotBlank(street, number, cityName, supermarket, supermarketBrandName)?.let {
        return it
    }
    return null
}

private fun getTags(entityContainer: EntityContainer?): Collection<Tag> {
    entityContainer?.entity?.tags?.run {
        return this
    }
    return mutableListOf()
}