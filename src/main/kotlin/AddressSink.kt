import org.geotools.api.feature.simple.SimpleFeature
import org.geotools.data.DataUtilities
import org.geotools.geojson.feature.FeatureJSON
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.task.v0_6.Sink
import java.io.File


class AddressSink : Sink {

    private var addresses = mutableSetOf<SimpleFeature>()
    private var addressProcessor = AddressProcessor(addresses)
    override fun close() {
        println("AddressSink is closed")
    }

    override fun complete() {
        println("AddressSink is completed")
        val featureCollection = DataUtilities.collection(addresses.toList())
        val geojson = FeatureJSON().toString(featureCollection)
        val file = File("src/main/resources/map-template.html")
        val html = file.bufferedReader().readText()
        val htmlWithGeosjon = html.replace("<GEOJSON>", geojson)
        val fileWithGeojson = File("src/main/resources/index.html")
        fileWithGeojson.writeText(htmlWithGeosjon)

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
