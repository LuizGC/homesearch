package com.homesearch.osmparser

import com.homesearch.osmparser.action.delete.CleanDB
import com.homesearch.osmparser.action.insert.AddressBulkInserter
import com.homesearch.osmparser.action.insert.ConvenienceStoreBulkInserter
import com.homesearch.osmparser.action.insert.PublicTransportationBulkInserter
import com.homesearch.osmparser.action.insert.SupermarketBulkInserter
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

    val addressBulkInserter = AddressBulkInserter()
    addressBulkInserter.bulkInsert(addressSink)

    val supermarketBulkInserter = SupermarketBulkInserter()
    supermarketBulkInserter.bulkInsert(addressSink)

    val convenienceStoreBulkInserter = ConvenienceStoreBulkInserter()
    convenienceStoreBulkInserter.bulkInsert(addressSink)

    val publicTransportationBulkInserter = PublicTransportationBulkInserter()
    publicTransportationBulkInserter.bulkInsert(addressSink)
}