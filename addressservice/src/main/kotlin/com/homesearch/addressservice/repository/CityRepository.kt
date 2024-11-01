package com.homesearch.addressservice.repository

import java.sql.DriverManager

private const val JDBC_URL = "jdbc:postgresql://localhost:5432/homesearch_db"
private const val USER_NAME = "admin"
private const val PASSWORD = "admin"
private const val CITY_QUERY = "SELECT DISTINCT INITCAP(city) city FROM addresses ORDER BY city;"

fun listCities(): List<String> {
    val cities = mutableListOf<String>()
    try {
        DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD).use { conn ->
            val statement = conn.createStatement()
            val resultSet = statement.executeQuery(CITY_QUERY)
            while (resultSet.next()) {
                cities += resultSet.getString("city")
            }
        }
    } catch (e : Exception) {
        println(e)
    }


    return cities
}