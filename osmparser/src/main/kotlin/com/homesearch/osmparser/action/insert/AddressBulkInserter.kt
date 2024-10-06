package com.homesearch.osmparser.action.insert

import com.homesearch.osmparser.db.DBBatchOperation
import com.homesearch.osmparser.osmdata.AddressSink
import org.locationtech.jts.geom.Point

private const val INSERT_QUERY =
    "INSERT INTO ADDRESSES(street, \"number\", city, location)  VALUES (?, ?, ?, ST_SetSRID(ST_Makepoint(?, ?), 4326));"

class AddressBulkInserter {

    val bulkInsert = DBBatchOperation(INSERT_QUERY);


    fun bulkInsert(addressSink: AddressSink) {
        bulkInsert.run { pstmt ->
            addressSink.addresses.forEach {
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