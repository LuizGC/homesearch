package com.homesearch.osmparser.osmdata

import org.geotools.api.feature.simple.SimpleFeature
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.task.v0_6.Sink


class AddressSink() : Sink {

    private var addresses = mutableSetOf<SimpleFeature>()
    private var addressProcessor = AddressProcessor(addresses)

    override fun close() {
        println("AddressSink is closed")
    }

    override fun complete() {
        println("AddressSink is completed")
    }

    override fun initialize(p0: MutableMap<String, Any>?) {
        println("initialize")
        addresses = mutableSetOf()
        addressProcessor = AddressProcessor(addresses)
    }

    override fun process(entityContainer: EntityContainer?) {
        entityContainer?.process(addressProcessor)
    }

    fun getAddresses(): Set<SimpleFeature> {
        return addresses
    }

    fun getSupermarkets(): Set<SimpleFeature> {
        return addresses.filter {
            it.getAttribute("isSupermarket").toString().toBoolean()
        }.toSet()
    }

    fun getConvenienceStore(): Set<SimpleFeature> {
        return addresses.filter {
            it.getAttribute("isConvenience").toString().toBoolean()
        }.toSet()
    }
}
