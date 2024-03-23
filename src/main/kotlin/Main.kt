package org.example

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType
import org.openstreetmap.osmosis.core.task.v0_6.Sink
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File

fun main() {
    val file = File("src/main/resources/pomorskie-latest.osm.pbf")
    val gdyniaCity = City("Gdynia")
    val osmosisReader = PbfReader(file, 0)
    osmosisReader.setSink(object : Sink {
        override fun close() {
            println("close")
        }

        override fun complete() {
            println("complete")
        }

        override fun initialize(p0: MutableMap<String, Any>?) {
            println("initialize")
        }

        override fun process(p0: EntityContainer?) {
//            p0?.entity?.tags?.filter {
//                it.key == "shop" && it.value == "supermarket"
//            }?.forEach {
//                println("${it.key}: ${it.value}")
//            }
            p0?.entity?.tags?.run {

                if (gdyniaCity.has(p0.entity)) {
                    if (p0.entity.tags.any { "addr:street".equals(it.key, true) }) {
                        p0.entity.tags.forEach {
                            println("${it.key}: ${it.value}")
                        }
                    }
                    println()
                }

            }
        }

    })

    osmosisReader.run();
}