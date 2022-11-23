package dev.sdckp.githubuserbrowser.repository.network.model

import com.google.gson.annotations.SerializedName

data class RepositoryInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("language")
    val language: String?
    // For this demo app we dont care about the rest of the fields in the response
)
