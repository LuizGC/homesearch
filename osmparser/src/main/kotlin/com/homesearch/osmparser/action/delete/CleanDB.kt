package com.homesearch.osmparser.action.delete

import com.homesearch.osmparser.db.DBBatchOperation
import org.flywaydb.core.Flyway

private const val CLEAN_ADDRESSES_TABLE_SQL = "truncate addresses cascade;"
private const val CLEAN_SUPERMARKETS_TABLE_SQL = "truncate supermarkets cascade;"
private const val CLEAN_CONVENIENCE_STORES_TABLE_SQL = "truncate convenience_stores cascade;"
private const val CLEAN_PUBLIC_TRANSPORTATIONS_TABLE_SQL = "truncate public_transportations cascade;"

private const val JDBC_URL = "jdbc:postgresql://localhost:5432/homesearch_db"
private const val USER_NAME = "admin"
private const val PASSWORD = "admin"

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
                .dataSource(JDBC_URL , USER_NAME , PASSWORD )
                .load()

        flyway.migrate()
    }
}