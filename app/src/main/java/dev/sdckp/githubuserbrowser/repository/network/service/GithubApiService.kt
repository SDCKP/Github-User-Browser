package dev.sdckp.githubuserbrowser.repository.network.service

import dev.sdckp.githubuserbrowser.repository.network.PATH_USERS
import dev.sdckp.githubuserbrowser.repository.network.PATH_USER_INFO
import dev.sdckp.githubuserbrowser.repository.network.PATH_USER_REPOSITORIES
import dev.sdckp.githubuserbrowser.repository.network.model.RepositoryInfo
import dev.sdckp.githubuserbrowser.repository.network.model.User
import dev.sdckp.githubuserbrowser.repository.network.model.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET(PATH_USERS)
    suspend fun getUsers(@Query("since") since: Int): List<User>

    @GET(PATH_USER_INFO)
    suspend fun getUserInfo(@Path("userId") userId: String): UserInfo

    @GET(PATH_USER_REPOSITORIES)
    suspend fun getUserRepositories(@Path("userId") userId: String): List<RepositoryInfo>

}