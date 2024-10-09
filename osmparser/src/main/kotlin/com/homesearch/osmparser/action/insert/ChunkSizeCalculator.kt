package com.homesearch.osmparser.action.insert

import java.util.stream.Stream
import kotlin.collections.chunked

private val CORES = Runtime.getRuntime().availableProcessors() + 1

fun calculateChunkSize(size: Int): Int = size / CORES

fun <T> generateChunk(list: Collection<T>): Stream<List<T>> {
    val chunkSize: Int = calculateChunkSize(list.size)
    return list.chunked(chunkSize).parallelStream()
}