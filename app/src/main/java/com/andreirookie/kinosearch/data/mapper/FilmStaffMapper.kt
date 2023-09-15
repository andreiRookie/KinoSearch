package com.andreirookie.kinosearch.data.mapper

import com.andreirookie.kinosearch.data.models.StaffNetModel
import com.andreirookie.kinosearch.domain.Staff
import javax.inject.Inject

class FilmStaffMapper @Inject constructor() : Mapper<StaffNetModel, Staff> {
    override fun mapFromEntity(e: StaffNetModel): Staff {
        return Staff(
            staffId = e.staffId,
            name = e.nameRu ?: e.nameEn ?: "",
            professionKey = e.professionKey
        )
    }
    override fun mapFromEntityList(list: List<StaffNetModel>): List<Staff> {
        return list.map { staffNetModel ->mapFromEntity(staffNetModel) }
    }
}