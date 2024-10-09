package com.homesearch.osmparser.osmdata

import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Node

private val PUBLIC_TRANSPORTATION_TAG_VALUES = listOf("stop_position", "platform", "station", "stop_area")

fun extractPublicTransportationData(nodeContainer: NodeContainer?, processNode: (OsmPublicTransportationData) -> Unit) {

    nodeContainer?.let { nodeContainer ->
        var name: String = "No Name"
        var isPublicTransportation = false
        getTags(nodeContainer).forEach {
            if (it.key == "public_transport" && it.value in PUBLIC_TRANSPORTATION_TAG_VALUES) {
                isPublicTransportation = true
            }
            if (it.key == "name") {
                name = it.value ?: name
            }
        }
        if (isPublicTransportation) {
            processNode(OsmPublicTransportationData(nodeContainer, name))
        }
    }
}

class OsmPublicTransportationData(nodeContainer: NodeContainer, val name: String) {

    private val node: Node = nodeContainer.entity

    fun getLatitude(): Double {
        return node.latitude
    }

    fun getLongitude(): Double {
        return node.longitude
    }

    override fun toString(): String {
        return "latitude=${getLatitude()}, longitude=${getLongitude()}, name=${name}"
    }

}