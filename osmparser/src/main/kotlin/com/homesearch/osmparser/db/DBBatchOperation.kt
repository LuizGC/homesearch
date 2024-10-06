package com.homesearch.osmparser.db

import java.sql.DriverManager
import java.sql.PreparedStatement
import kotlin.use

class DBBatchOperation(val insertSql: String) {

    object DbConstants {
        init {
            Class.forName("org.postgresql.Driver");
        }

        const val JDBC_URL = "jdbc:postgresql://localhost:5432/homesearch_db"
        const val USER_NAME = "admin"
        const val PASSWORD = "admin"
    }

    fun run(batchOperation: (PreparedStatement) -> Unit) {
        DriverManager.getConnection(DbConstants.JDBC_URL, DbConstants.USER_NAME, DbConstants.PASSWORD).use { conn ->
            conn.autoCommit = false
            val pstmt = conn.prepareStatement(insertSql)

            batchOperation(pstmt);

            pstmt.executeBatch()
            conn.commit()
            conn.autoCommit = true
        }
    }


}