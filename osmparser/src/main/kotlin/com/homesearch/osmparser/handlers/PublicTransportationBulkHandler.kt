package com.homesearch.osmparser.handlers

import com.homesearch.common.db.DBBatchOperation
import com.homesearch.osmparser.osmdata.AddressSink

private const val INSERT_QUERY =
    "INSERT INTO PUBLIC_TRANSPORTATIONS(name, address_id) VALUES (?, (select a.id from addresses a where ST_intersects(ST_Buffer(ST_SetSRID(ST_Makepoint(?, ?), 4326)::geography, 250), a.location) order by ST_Distance(a.location, ST_SetSRID(ST_Makepoint(?, ?), 4326), false) limit 1)) ON CONFLICT DO NOTHING;"

class PublicTransportationBulkHandler {
    val bulkInsert = DBBatchOperation(INSERT_QUERY)

    fun bulkInsert(addressSink: AddressSink) {
        println("Insert Public Transportation")
        generateChunk(addressSink.getPublicTransportation())
            .forEach { publicTransportationChunk ->
                bulkInsert.run { pstmt ->
                    publicTransportationChunk.forEach {
                        pstmt.setString(1, it.name)
                        pstmt.setDouble(2, it.getLongitude())
                        pstmt.setDouble(3, it.getLatitude())
                        pstmt.setDouble(4, it.getLongitude())
                        pstmt.setDouble(5, it.getLatitude())
                        pstmt.addBatch()
                    }
                }
            }
    }
}