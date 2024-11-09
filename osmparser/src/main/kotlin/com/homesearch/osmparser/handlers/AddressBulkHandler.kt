package com.homesearch.osmparser.handlers

import com.homesearch.common.db.DBBatchOperation
import com.homesearch.osmparser.osmdata.AddressSink
import org.locationtech.jts.geom.Point

private const val INSERT_QUERY =
    "INSERT INTO ADDRESSES(street, \"number\", city, location)  VALUES (?, ?, ?, ST_SetSRID(ST_Makepoint(?, ?), 4326)) ON CONFLICT DO NOTHING;"

class AddressBulkHandler {

    val bulkInsert = DBBatchOperation(INSERT_QUERY)

    fun bulkInsert(addressSink: AddressSink) {
        println("Insert Addresses")
        bulkInsert.run { pstmt ->
            addressSink.getAddresses().forEach {
                val point = it.defaultGeometry
                if (point is Point) {
                    pstmt.setString(1, it.getAttribute("street").toString())
                    pstmt.setString(2, it.getAttribute("number").toString())
                    pstmt.setString(3, it.getAttribute("city").toString())
                    pstmt.setDouble(4, point.x)
                    pstmt.setDouble(5, point.y)
                    pstmt.addBatch()
                }
            }
        }
    }
}