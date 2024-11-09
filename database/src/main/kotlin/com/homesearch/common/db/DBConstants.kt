package com.homesearch.common.db

class DBConstants {
    companion object {
        private val DB_HOST = System.getenv("JDBC_URL") ?: "localhost"
        private val JDBC_URL = "jdbc:postgresql://${DB_HOST}:5432/homesearch_db"
        private val USERNAME = System.getenv("USERNAME") ?: "admin"
        private val PASSWORD = System.getenv("PASSWORD") ?: "admin"

        fun getJDBCUrl(): String {
            return JDBC_URL
        }

        fun getUsername(): String {
            return USERNAME;
        }

        fun getPassword(): String {
            return PASSWORD
        }
    }


}
