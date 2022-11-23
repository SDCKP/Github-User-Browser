package dev.sdckp.githubuserbrowser.repository.network.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("bio")
    val bio: String?,
    // For this demo app we dont care about the rest of the fields in the response
    val repositoryList: List<RepositoryInfo> = listOf()
)
