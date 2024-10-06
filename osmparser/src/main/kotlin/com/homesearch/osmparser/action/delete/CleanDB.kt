package com.homesearch.osmparser.action.delete

import com.homesearch.osmparser.db.DBBatchOperation

private const val CLEAN_ADDRESSES_TABLE_SQL = "delete from addresses;"
private const val CLEAN_SUPERMARKETS_TABLE_SQL = "delete from supermarkets;"

class CleanDB {

    fun cleanAllTables() {
        println("Clean Database Started")
        cleanSupermarkets()
        cleanAddresses()
    }

    fun cleanAddresses() {
        println("Clean Addresses")
        val dbBulkOperation = DBBatchOperation(CLEAN_ADDRESSES_TABLE_SQL);
        dbBulkOperation.run {
            it.addBatch()
        }
    }

    fun cleanSupermarkets() {
        println("Clean Supermarkets")
        val dbBulkOperation = DBBatchOperation(CLEAN_SUPERMARKETS_TABLE_SQL);
        dbBulkOperation.run {
            it.addBatch()
        }
    }

}