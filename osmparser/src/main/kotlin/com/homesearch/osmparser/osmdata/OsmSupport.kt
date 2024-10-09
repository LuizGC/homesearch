package com.homesearch.osmparser.osmdata

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Tag

fun getTags(entityContainer: EntityContainer?): Collection<Tag> {
    entityContainer?.entity?.tags?.run {
        return this
    }
    return mutableListOf()
}