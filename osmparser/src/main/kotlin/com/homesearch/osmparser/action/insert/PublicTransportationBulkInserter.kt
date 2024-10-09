package com.homesearch.osmparser.action.insert

import com.homesearch.osmparser.db.DBBatchOperation
import com.homesearch.osmparser.osmdata.AddressSink

private const val INSERT_QUERY =
    "INSERT INTO PUBLIC_TRANSPORTATIONS(name, address_id) VALUES (?, (select a.id from addresses a where ST_intersects(ST_Buffer(ST_SetSRID(ST_Makepoint(?, ?), 4326)::geography, 250), a.location) order by ST_Distance(a.location, ST_SetSRID(ST_Makepoint(?, ?), 4326), false) limit 1)) ON CONFLICT DO NOTHING;"

class PublicTransportationBulkInserter {
    val bulkInsert = DBBatchOperation(INSERT_QUERY)

    fun bulkInsert(addressSink: AddressSink) {
        println("Insert Public Transportation")
        val publicTransportationList = addressSink.getPublicTransportation()
        val chunkSize: Int = publicTransportationList.size / Runtime.getRuntime().availableProcessors()
        publicTransportationList.chunked(chunkSize)
            .parallelStream()
            .forEach { publicTransportationList ->
                bulkInsert.run { pstmt ->
                    publicTransportationList.forEach {
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