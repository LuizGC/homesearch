import org.example.City
import org.openstreetmap.osmosis.core.container.v0_6.*
import org.openstreetmap.osmosis.core.domain.v0_6.Tag

class AddressProcessor(val addresses: MutableSet<Address>) : EntityProcessor {

    override fun process(p0: BoundContainer?) {
        println("bound")
    }


    override fun process(node: NodeContainer?) {
        extractAddress(node)?.let {
            addresses.add(it)
            //println("${node?.entity?.latitude}, ${node?.entity?.longitude}")
        }
    }

    override fun process(way: WayContainer?) {
        extractAddress(way)?.let {
            addresses.add(it)
        }
    }

    override fun process(relation: RelationContainer?) {
        extractAddress(relation)?.let {
            addresses.add(it)
        }
    }

    private fun extractAddress(entityContainer: EntityContainer?): Address? {
        var name: String? = null
        var number: String? = null
        var cityName: String? = null
        var supermarket = false
        var supermarketBrandName: String? = null
        getTags(entityContainer).forEach {
            when (it.key) {
                "addr:street" -> name = it.value
                "addr:housenumber" -> number = it.value
                "addr:city" -> cityName = it.value
                "shop" -> supermarket = it.value == "supermarket"
                "name" -> supermarketBrandName = it.value
            }
        }
        createAddressIfNotBlank(name, number, cityName, supermarket, supermarketBrandName)?.let {
            return it
        }
        return null
    }

    private fun createAddressIfNotBlank(
        name: String?,
        number: String?,
        cityName: String?,
        supermarket: Boolean,
        supermarketBrandName: String?
    ): Address? {
        if (listOf(name, number, cityName).any { it.isNullOrBlank() }) {
            return null
        }
        if (supermarket) {
            return Supermarket(name.orEmpty(), number.orEmpty(), City(cityName.orEmpty()), supermarketBrandName.orEmpty())
        }
        return BaseAddress(name.orEmpty(), number.orEmpty(), City(cityName.orEmpty()))
    }

    private fun getTags(entityContainer: EntityContainer?): Collection<Tag> {
        entityContainer?.entity?.tags?.run {
            return this
        }
        return mutableListOf()
    }

}