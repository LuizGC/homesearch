package com.homesearch.osmparser

import com.homesearch.common.db.CleanDB
import com.homesearch.osmparser.handlers.AddressBulkHandler
import com.homesearch.osmparser.handlers.ConvenienceStoreBulkHandler
import com.homesearch.osmparser.handlers.PublicTransportationBulkHandler
import com.homesearch.osmparser.handlers.SupermarketBulkHandler
import com.homesearch.osmparser.osmdata.AddressSink
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File

fun main() {
    Class.forName("org.postgresql.Driver")

    val cleanDB = CleanDB()
    cleanDB.cleanAllTables()

    val file = File("osmParser/src/main/resources/pomorskie-latest.osm.pbf")
    val osmosisReader = PbfReader(file, Runtime.getRuntime().availableProcessors())
    val addressSink = AddressSink()
    osmosisReader.setSink(addressSink)
    osmosisReader.run()

    val addressBulkHandler = AddressBulkHandler()
    addressBulkHandler.bulkInsert(addressSink)

    val supermarketBulkHandler = SupermarketBulkHandler()
    supermarketBulkHandler.bulkInsert(addressSink)

    val convenienceStoreBulkHandler = ConvenienceStoreBulkHandler()
    convenienceStoreBulkHandler.bulkInsert(addressSink)

    val publicTransportationBulkHandler = PublicTransportationBulkHandler()
    publicTransportationBulkHandler.bulkInsert(addressSink)
}