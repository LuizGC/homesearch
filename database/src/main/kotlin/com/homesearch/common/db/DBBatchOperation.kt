package com.homesearch.common.db

import com.homesearch.common.db.DBConstants.Companion.getJDBCUrl
import com.homesearch.common.db.DBConstants.Companion.getPassword
import com.homesearch.common.db.DBConstants.Companion.getUsername
import java.sql.DriverManager
import java.sql.PreparedStatement

class DBBatchOperation(val insertSql: String) {

    fun run(batchOperation: (PreparedStatement) -> Unit) {

        DriverManager.getConnection(
            getJDBCUrl(), getUsername(), getPassword()
        ).use { conn ->
            conn.autoCommit = false
            val pstmt = conn.prepareStatement(insertSql)

            batchOperation(pstmt)

            pstmt.executeBatch()
            conn.commit()
            conn.autoCommit = true
        }
    }


}