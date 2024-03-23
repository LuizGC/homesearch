package org.example

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Node
import org.openstreetmap.osmosis.core.task.v0_6.Sink
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val file = File("src/main/resources/pomorskie-latest.osm.pbf")
    val gdyniaCity = City("Gdynia")
    val nodes = mutableMapOf<Long, Node>()
    val osmosisReader = PbfReader(file, 0)
    val sum = AtomicLong(0)
    osmosisReader.setSink(object : Sink {
        override fun close() {
            println("close")
        }

        override fun complete() {
            println("complete")
            println(sum)
        }

        override fun initialize(p0: MutableMap<String, Any>?) {
            println("initialize")
        }

        override fun process(p0: EntityContainer?) {

            p0?.entity?.run {
                if (this is Node) {
                    nodes.put(this.id, this)?.run {
                        println(this)
                    }
                    sum.incrementAndGet()
                }
            }
//            p0?.entity?.tags?.run {
//
//                if (gdyniaCity.has(p0.entity)) {
//                    if (p0.entity.tags.any { it.key == "shop" && it.value == "supermarket" }) {
//                        p0.entity.tags.forEach {
//                            println("${it.key}: ${it.value}")
//                        }
//                        println()
//                    }
//                }
//
//            }
        }

    })

    osmosisReader.run();
}