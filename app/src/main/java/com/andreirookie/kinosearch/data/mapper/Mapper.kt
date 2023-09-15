package com.andreirookie.kinosearch.data.mapper

interface Mapper<E, M> {
    fun mapFromEntity(e: E): M
    fun mapFromEntityList(list: List<E>): List<M>
}