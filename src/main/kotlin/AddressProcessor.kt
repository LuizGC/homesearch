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
            println("${node?.entity?.latitude}, ${node?.entity?.longitude}")
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
        getTags(entityContainer).forEach {
            when (it.key) {
                "addr:street" -> name = it.value
                "addr:housenumber" -> number = it.value
                "addr:city" -> cityName = it.value
            }
        }
        createAddressIfNotBlank(name, number, cityName)?.let {
             return it
        }
        return null
    }

    private fun createAddressIfNotBlank(name: String?, number: String?, cityName: String?): Address? {
        if (listOf(name, number, cityName).all { !it.isNullOrBlank() }) {
            return Address(name.orEmpty(), number.orEmpty(), City(cityName.orEmpty()))
        }
        return null
    }

    private fun getTags(entityContainer: EntityContainer?): Collection<Tag> {
        entityContainer?.entity?.tags?.run {
            return this
        }
        return mutableListOf()
    }

}