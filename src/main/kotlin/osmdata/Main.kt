package osmdata

import org.geotools.data.DataUtilities
import org.geotools.geojson.feature.FeatureJSON
import java.io.File
import java.nio.file.Files
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


fun main() {
//    val file = File("src/main/resources/pomorskie-latest.osm.pbf")
//    val osmosisReader = PbfReader(file, Runtime.getRuntime().availableProcessors())
//    val addressSink = AddressSink()
//    osmosisReader.setSink(addressSink)
//    osmosisReader.run()

    val jdbcUrl = "jdbc:postgresql://localhost:5432/homesearch_db"
    val username = "admin"
    val password = "admin"


    // Register the PostgreSQL driver
    Class.forName("org.postgresql.Driver")


    // Connect to the database
    val connection: Connection = DriverManager.getConnection(jdbcUrl, username, password)

    val statement: Statement = connection.createStatement()

    val resultSet: ResultSet = statement.executeQuery("select * from spatial_ref_sys")

    while (resultSet.next()) {
        val columnValue = resultSet.getString("srid")
        println("Column Value: $columnValue")
    }

    // Perform desired database operations

    // Close the connection
    connection.close()

    //createCheckHtml(addressSink)
}

private fun createCheckHtml(addressSink: AddressSink) {
    val addresses = addressSink.addresses.filter {
        it.getAttribute("name") != null
    }
    val featureCollection = DataUtilities.collection(addresses)
    val geojson = FeatureJSON().toString(featureCollection)
    val template = File("src/main/resources/map-template.html")
    val html = template.bufferedReader().readText()
    val htmlWithGeosjon = html.replace("<GEOJSON>", geojson)
    val fileWithGeojson = Files.createTempFile("map", ".html").toFile()
    fileWithGeojson.writeText(htmlWithGeosjon)
    println("file://${fileWithGeojson.absolutePath}")
}
