package com.homesearch.osmparser.action.delete

import com.homesearch.osmparser.db.DBBatchOperation

private const val CLEAN_ADDRESS_TABLE_SQL = "delete from public.address;"


class CleanDB {

    fun cleanAddress() {
        val dbBulkOperation = DBBatchOperation(CLEAN_ADDRESS_TABLE_SQL);
        dbBulkOperation.run {
            it.executeBatch()
        }
    }

}