package com.homesearch.common.db

import com.homesearch.common.db.DBConstants.Companion.getJDBCUrl
import com.homesearch.common.db.DBConstants.Companion.getPassword
import com.homesearch.common.db.DBConstants.Companion.getUsername
import org.flywaydb.core.Flyway

private const val CLEAN_ADDRESSES_TABLE_SQL = "truncate addresses cascade;"
private const val CLEAN_SUPERMARKETS_TABLE_SQL = "truncate supermarkets cascade;"
private const val CLEAN_CONVENIENCE_STORES_TABLE_SQL = "truncate convenience_stores cascade;"
private const val CLEAN_PUBLIC_TRANSPORTATIONS_TABLE_SQL = "truncate public_transportations cascade;"

class CleanDB {

    fun cleanAllTables() {
        runMigration()
        println("Clean Database Started")
        cleanTable(CLEAN_PUBLIC_TRANSPORTATIONS_TABLE_SQL)
        cleanTable(CLEAN_CONVENIENCE_STORES_TABLE_SQL)
        cleanTable(CLEAN_SUPERMARKETS_TABLE_SQL)
        cleanTable(CLEAN_ADDRESSES_TABLE_SQL)
    }

    private fun cleanTable(deleteSql: String) {
        println("Run ${deleteSql}")
        val dbBulkOperation = DBBatchOperation(deleteSql)
        dbBulkOperation.run {
            it.addBatch()
        }
    }

    private fun runMigration() {
        val flyway =
            Flyway.configure()
                .dataSource(getJDBCUrl(), getUsername(), getPassword())
                .load()

        flyway.migrate()
    }
}