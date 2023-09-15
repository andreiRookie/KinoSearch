package com.andreirookie.kinosearch.data.models

import com.squareup.moshi.Json

data class StaffNetModel(
    @field:Json(name = "staffId")
    val staffId: Int,
    @field:Json(name = "nameRu")
    val nameRu: String?,
    @field:Json(name = "nameEn")
    val nameEn: String?,
    @field:Json(name = "professionKey")
    val professionKey: String = ""
)
