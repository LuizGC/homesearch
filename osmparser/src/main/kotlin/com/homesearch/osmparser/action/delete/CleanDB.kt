package com.homesearch.osmparser.action.delete

import com.homesearch.osmparser.db.DBBatchOperation

private const val CLEAN_ADDRESSES_TABLE_SQL = "delete from addresses;"
private const val CLEAN_SUPERMARKETS_TABLE_SQL = "delete from supermarkets;"

class CleanDB {

    fun cleanAllTables() {
        cleanSupermarkets()
        cleanAddresses()
    }

    fun cleanAddresses() {
        val dbBulkOperation = DBBatchOperation(CLEAN_ADDRESSES_TABLE_SQL);
        dbBulkOperation.run {
            it.executeBatch()
        }
    }

    fun cleanSupermarkets() {
        val dbBulkOperation = DBBatchOperation(CLEAN_SUPERMARKETS_TABLE_SQL);
        dbBulkOperation.run {
            it.executeBatch()
        }
    }

}