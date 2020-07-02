package com.jeff.startproject.view.table.paging

import androidx.paging.DataSource
import com.jeff.startproject.model.api.vo.UserItem

class UserFactory constructor(
    private val userDataSource: UserDataSource
) : DataSource.Factory<String, UserItem>() {
    override fun create(): DataSource<String, UserItem> {
        return userDataSource
    }
}

