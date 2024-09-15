package com.homesearch.osmparser.osmdata

import org.geotools.api.feature.simple.SimpleFeature
import org.openstreetmap.osmosis.core.container.v0_6.BoundContainer
import org.openstreetmap.osmosis.core.container.v0_6.EntityProcessor
import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer
import org.openstreetmap.osmosis.core.container.v0_6.RelationContainer
import org.openstreetmap.osmosis.core.container.v0_6.WayContainer


class AddressProcessor(val addresses: MutableSet<SimpleFeature>) : EntityProcessor {

    override fun process(p0: BoundContainer?) {
    }


    override fun process(node: NodeContainer?) {
        extractAddress(node)?.let {
            addresses.add(it)
        }
    }

    override fun process(way: WayContainer?) {
        extractAddress(way)?.let {
            addresses.add(it)
        }
    }

    override fun process(relation: RelationContainer?) {
    //        extractAddress(relation)?.let {
    //            addresses.add(it)
    //        }
    }

}