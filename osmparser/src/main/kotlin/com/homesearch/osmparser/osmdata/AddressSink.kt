package com.homesearch.osmparser.osmdata

import org.geotools.api.feature.simple.SimpleFeature
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.task.v0_6.Sink


class AddressSink() : Sink {

    private lateinit var addresses: MutableSet<SimpleFeature>
    private lateinit var publicTransportation: MutableSet<OsmPublicTransportationData>
    private lateinit var addressProcessor: AddressProcessor

    init {
        cleanData()
    }

    override fun close() {
        println("AddressSink is closed")
    }

    override fun complete() {
        println("AddressSink is completed")
    }

    override fun initialize(p0: MutableMap<String, Any>?) {
        println("initialize")
        cleanData()
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

    fun getPublicTransportation(): Set<OsmPublicTransportationData> {
        return publicTransportation
    }

    private fun cleanData() {
        addresses = mutableSetOf()
        publicTransportation = mutableSetOf()
        addressProcessor = AddressProcessor(addresses, publicTransportation)
    }
}
