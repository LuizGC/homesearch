package com.homesearch.osmparser.action.delete

import com.homesearch.osmparser.db.DBBatchOperation

private const val CLEAN_ADDRESSES_TABLE_SQL = "delete from addresses;"
private const val CLEAN_SUPERMARKETS_TABLE_SQL = "delete from supermarkets;"
private const val CLEAN_CONVENIENCE_STORES_TABLE_SQL = "delete from convenience_stores;"

class CleanDB {

    fun cleanAllTables() {
        println("Clean Database Started")
        cleanTable(CLEAN_CONVENIENCE_STORES_TABLE_SQL)
        cleanTable(CLEAN_SUPERMARKETS_TABLE_SQL)
        cleanTable(CLEAN_ADDRESSES_TABLE_SQL)
    }

    fun cleanTable(deleteSql: String) {
        println("Run ${deleteSql}")
        val dbBulkOperation = DBBatchOperation(deleteSql)
        dbBulkOperation.run {
            it.addBatch()
        }
    }
}