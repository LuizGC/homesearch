package org.example

import AddressSink
import org.geotools.data.DataUtilities
import org.geotools.geojson.feature.FeatureJSON
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File

fun main() {
    val file = File("src/main/resources/pomorskie-latest.osm.pbf")
    val osmosisReader = PbfReader(file, Runtime.getRuntime().availableProcessors())
    val addressSink = AddressSink()
    osmosisReader.setSink(addressSink)
    osmosisReader.run()
    val addresses = addressSink.addresses.filter {
        it.getAttribute("name") != null
    }
    val featureCollection = DataUtilities.collection(addresses)
    val geojson = FeatureJSON().toString(featureCollection)
    val template = File("src/main/resources/map-template.html")
    val html = template.bufferedReader().readText()
    val htmlWithGeosjon = html.replace("<GEOJSON>", geojson)
    val fileWithGeojson = File("src/main/resources/index.html")
    fileWithGeojson.writeText(htmlWithGeosjon)
}