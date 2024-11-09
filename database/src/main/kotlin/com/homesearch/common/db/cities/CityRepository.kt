package com.homesearch.common.db.cities

import com.homesearch.common.db.DBConstants.Companion.getJDBCUrl
import com.homesearch.common.db.DBConstants.Companion.getPassword
import com.homesearch.common.db.DBConstants.Companion.getUsername
import java.sql.DriverManager

private const val CITY_QUERY = "SELECT DISTINCT INITCAP(city) city FROM addresses ORDER BY city;"

fun listCities(): List<String> {
    val cities = mutableListOf<String>()
    try {
        DriverManager.getConnection(getJDBCUrl(), getUsername(), getPassword()).use { conn ->
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