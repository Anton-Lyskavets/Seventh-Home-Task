package com.example.seventh_home_task.domain.models

import com.squareup.moshi.Json

data class Meta(
    val title: String,
    val image: String,
    val fields: List<Fields>
)

data class Fields(
    @Json(name = "title") val nameField: String,
    @Json(name = "name") val inputField: String,
    @Json(name = "type") val typeField: String,
) {
    @Json(name = "values")
    lateinit var valuesList: Map<String, String>
}