package com.javaria.cats.data.models

data class CatFactsResponse(
        val status: FactStatus,
        val type: String,
        val deleted: Boolean,
        val _id: String,
        val text: String,
        val user: String,
        val __v: String,
        val source: String,
        val updatedAt: String,
        val createdAt: String,
        val used: Boolean,
)

data class FactStatus (
        val verified: Boolean,
        val sentCount: String,
        val feedback: String?
)
