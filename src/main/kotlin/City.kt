package org.example

import org.openstreetmap.osmosis.core.domain.v0_6.Entity

data class City(val name: String?) {

    fun has(entity: Entity?): Boolean {
        return entity?.tags?.filter {
            it.key == "addr:city" && name.equals(it.value, true)
        }?.isNotEmpty() ?: false
    }

}


