package dev.sdckp.githubuserbrowser.repository

import androidx.annotation.VisibleForTesting
import dev.sdckp.githubuserbrowser.repository.network.model.User
import dev.sdckp.githubuserbrowser.repository.network.model.UserInfo
import dev.sdckp.githubuserbrowser.repository.network.service.GithubApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class Repository @Inject constructor(
    private val githubApiService: GithubApiService
) {

    companion object {
        // The cache time for the data.
        // If cache time expires or not set we retrieve from network source, else retrieve from local source
        val USER_LIST_TTL: Pair<String, Duration> = Pair("USER_LIST", 30.minutes)
        val USER_INFO_TTL: Pair<String, Duration> = Pair("USER_INFO", 5.minutes)
    }

    suspend fun getUserList(since: Int): Flow<List<User>> = flow {
        runCatching {
            // We could expand this to cache the response based on some specific TTL for the data like this
            /*if (isCacheExpired(USER_LIST_TTL)) {
                getUserListFromNetwork(since)
            } else {
                getUserListFromDatabase(since)
            }*/
            // On this case for simplicity reasons we will obtain it only from network
            getUserListFromNetwork(since)
        }.onFailure { error ->
            when (error) {
                is UnknownHostException -> {
                    // No internet, we could get the data from local database if available
                    runCatching {
                        //getUserListFromDatabase()
                    }.onFailure {
                        throw it
                    }.onSuccess { userList ->
                        //emit(userList)
                    }
                }
                else -> throw error
            }
        }.onSuccess { userList ->
            emit(userList)
        }
    }

    @VisibleForTesting
    suspend fun getUserListFromNetwork(since: Int): List<User> {
        // Retrieve updated data from network
        return githubApiService.getUsers(since)
    }

    suspend fun getUserInfo(userId: String): Flow<UserInfo> = flow {
        runCatching {
            // We could expand this to cache the response based on some specific TTL for the data like this
            /*if (isCacheExpired(USER_LIST_TTL)) {
                getUserInfoFromNetwork(userId)
            } else {
                getUserInfoFromDatabase(userId)
            }*/
            // On this case for simplicity reasons we will obtain it only from network
            getUserInfoFromNetwork(userId)
        }.onFailure { error ->
            when (error) {
                is UnknownHostException -> {
                    // No internet, we could get the data from local database if available
                    runCatching {
                        //getUserListFromDatabase()
                    }.onFailure {
                        throw it
                    }.onSuccess { userInfo ->
                        //emit(userInfo)
                    }
                }
                else -> throw error
            }
        }.onSuccess { userList ->
            emit(userList)
        }
    }

    @VisibleForTesting
    suspend fun getUserInfoFromNetwork(userId: String): UserInfo {
        // Get basic user info
        val userInfo = githubApiService.getUserInfo(userId)
        // Get repositories owned by the user
        val repositories = githubApiService.getUserRepositories(userId)
        // Return a single item with all data merged
        return userInfo.copy(repositoryList = repositories)
    }

}