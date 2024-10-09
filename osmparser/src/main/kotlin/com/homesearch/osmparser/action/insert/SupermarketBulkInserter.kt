package com.homesearch.osmparser.action.insert

import com.homesearch.osmparser.db.DBBatchOperation
import com.homesearch.osmparser.osmdata.AddressSink

private const val INSERT_QUERY =
    "INSERT INTO SUPERMARKETS(name, address_id)  VALUES (?, (select a.id from addresses a where street=? and number=? and city=?)) ON CONFLICT DO NOTHING;"

class SupermarketBulkInserter {

    val bulkInsert = DBBatchOperation(INSERT_QUERY)

    fun bulkInsert(addressSink: AddressSink) {
        println("Insert Supermarkets")
        generateChunk(addressSink.getSupermarkets()).forEach { chunk ->
            bulkInsert.run { pstmt ->
                chunk.forEach {
                    pstmt.setString(1, it.getAttribute("name")?.toString())
                    pstmt.setString(2, it.getAttribute("street").toString())
                    pstmt.setString(3, it.getAttribute("number").toString())
                    pstmt.setString(4, it.getAttribute("city").toString())
                    pstmt.addBatch()
                }
            }
        }
    }
}