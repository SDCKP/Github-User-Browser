package dev.sdckp.githubuserbrowser.screen.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sdckp.githubuserbrowser.repository.Repository
import dev.sdckp.githubuserbrowser.repository.network.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    private var isLoadingData = false

    fun loadData() {
        if (isLoadingData) return
        isLoadingData = true
        viewModelScope.launch {
            // Load user list from data source flow. For pagination, provide the ID of the last user loaded
            val userListDataFlow = repository.getUserList(userList.value?.last()?.id ?: 0)
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    error.printStackTrace()
                    // To avoid reaching rate limits on non-auth API usage, disable loading on error by not resetting the flag
                    // isLoadingData = false
                    // Possible improvement: Error handling
                }

            userListDataFlow.collect { userList ->
                _userList.value = userList
                isLoadingData = false
            }
        }
    }

    fun loadNextItemsIfNeeded(lastVisibleItem: Int, totalItemsInList: Int) {
        // Pagination: Start loading the next items onto the list when we are about to reach the end
        if (lastVisibleItem + 3 > totalItemsInList) {
            loadData()
        }
    }

}