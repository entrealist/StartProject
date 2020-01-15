package com.jeff.startproject.view.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jeff.startproject.model.api.ApiRepository
import com.jeff.startproject.model.api.user.UserItem
import com.jeff.startproject.view.base.BaseViewModel
import com.jeff.startproject.view.table.paging.PagingCallback
import com.jeff.startproject.view.table.paging.UserDataSource
import com.jeff.startproject.view.table.paging.UserFactory
import com.log.JFLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.inject

class TableViewModel : BaseViewModel() {

    private val apiRepository: ApiRepository by inject()

    private val _userListData = MutableLiveData<PagedList<UserItem>>()
    val userListData: LiveData<PagedList<UserItem>> = _userListData

    fun getUsers() {
        viewModelScope.launch {
            getPagingItems().asFlow().collect {
                _userListData.postValue(it)
            }
        }
    }

    private fun getPagingItems(): LiveData<PagedList<UserItem>> {
        val userDataSource = UserDataSource(viewModelScope, apiRepository, callback)
        val userFactory = UserFactory(userDataSource)
        val config = PagedList.Config.Builder().setPageSize(20).build()
        return LivePagedListBuilder(userFactory, config).build()
    }

    private val callback = object : PagingCallback {
        override fun onLoading() {

        }

        override fun onLoaded() {

        }

        override fun onTotalCount(count: Int) {

        }

        override fun onThrowable(throwable: Throwable) {
            JFLog.e(throwable)
        }
    }
}