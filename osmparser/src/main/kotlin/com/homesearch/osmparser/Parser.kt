package com.homesearch.osmparser

import com.homesearch.osmparser.action.delete.CleanDB
import com.homesearch.osmparser.action.insert.AddressBulkInserter
import com.homesearch.osmparser.osmdata.AddressSink
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File

fun main() {
    val file = File("osmParser/src/main/resources/pomorskie-latest.osm.pbf")
    val osmosisReader = PbfReader(file, Runtime.getRuntime().availableProcessors())
    val addressSink = AddressSink()
    osmosisReader.setSink(addressSink)
    osmosisReader.run()

    val cleanDB = CleanDB()
    cleanDB.cleanAllTables()

    val addressBulkInserter = AddressBulkInserter()
    addressBulkInserter.bulkInsert(addressSink)
}