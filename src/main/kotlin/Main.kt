package org.example

import AddressSink
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File

fun main() {
    val file = File("src/main/resources/pomorskie-latest.osm.pbf")
    val osmosisReader = PbfReader(file, Runtime.getRuntime().availableProcessors())
    osmosisReader.setSink(AddressSink())
    osmosisReader.run()
}