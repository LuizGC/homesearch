import org.geotools.api.feature.simple.SimpleFeature
import org.geotools.geojson.feature.FeatureJSON
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.task.v0_6.Sink

class AddressSink : Sink {

    private var addresses = mutableSetOf<SimpleFeature>()
    private var addressProcessor = AddressProcessor(addresses)
    override fun close() {
        println("AddressSink is closed")
    }

    override fun complete() {
        println("AddressSink is completed")
        addresses.forEach {
            println(FeatureJSON().toString(it))
        }
    }

    override fun initialize(p0: MutableMap<String, Any>?) {
        println("initialize")
        addresses = mutableSetOf()
        addressProcessor = AddressProcessor(addresses)
    }

    override fun process(entityContainer: EntityContainer?) {
        entityContainer?.process(addressProcessor)
    }

}
