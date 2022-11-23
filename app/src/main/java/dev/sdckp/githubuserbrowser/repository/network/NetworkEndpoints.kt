package dev.sdckp.githubuserbrowser.repository.network

const val BASE_URL: String = "https://api.github.com/"
const val PATH_USERS: String = "users"
const val PATH_USER_INFO: String = "$PATH_USERS/{userId}"
const val PATH_USER_REPOSITORIES: String = "$PATH_USERS/{userId}/repos"