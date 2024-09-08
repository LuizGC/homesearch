package osmdata

import org.geotools.data.DataUtilities
import org.geotools.geojson.feature.FeatureJSON
import org.locationtech.jts.geom.Point
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File
import java.nio.file.Files
import java.sql.DriverManager


fun main() {
    val file = File("src/main/resources/pomorskie-latest.osm.pbf")
    val osmosisReader = PbfReader(file, Runtime.getRuntime().availableProcessors())
    val addressSink = AddressSink()
    osmosisReader.setSink(addressSink)
    osmosisReader.run()

    val jdbcUrl = "jdbc:postgresql://localhost:5432/homesearch_db"
    val username = "admin"
    val password = "admin"
    val sql = "INSERT INTO public.address(street, \"number\", city, location)  VALUES (?, ?, ?, ST_SetSRID(ST_Makepoint(?, ?), 4326));"
    val cleanTable = "delete from public.address;"

    // Register the PostgreSQL driver
    Class.forName("org.postgresql.Driver")

    DriverManager.getConnection(jdbcUrl, username, password).use { conn ->
        val pstmt = conn.prepareStatement(cleanTable)
        pstmt.executeUpdate()
    }

    // Connect to the database
    DriverManager.getConnection(jdbcUrl, username, password).use { conn ->
        addressSink.addresses.forEach {
            val point = it.defaultGeometry
            if (point is Point) {
                val pstmt = conn.prepareStatement(sql)
                pstmt.setString(1, it.getAttribute("street").toString())
                pstmt.setString(2, it.getAttribute("number").toString())
                pstmt.setString(3, it.getAttribute("city").toString())
                pstmt.setDouble(4, point.x)
                pstmt.setDouble(5, point.y)
                pstmt.executeUpdate()
            }
        }
    }


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
