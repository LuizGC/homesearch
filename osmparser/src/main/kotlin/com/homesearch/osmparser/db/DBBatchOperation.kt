package com.homesearch.osmparser.db

import java.sql.DriverManager
import java.sql.PreparedStatement
import kotlin.use

private const val JDBC_URL = "jdbc:postgresql://localhost:5432/homesearch_db"
private const val USER_NAME = "admin"
private const val PASSWORD = "admin"

class DBBatchOperation(val insertSql: String) {

    fun run(batchOperation: (PreparedStatement) -> Unit) {

        DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD).use { conn ->
            conn.autoCommit = false
            val pstmt = conn.prepareStatement(insertSql)

            batchOperation(pstmt)

            pstmt.executeBatch()
            conn.commit()
            conn.autoCommit = true
        }
    }


}