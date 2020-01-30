package com.jeff.startproject.view.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeff.startproject.dao.UserDao
import com.jeff.startproject.model.db.DbResult
import com.jeff.startproject.model.db.User
import com.jeff.startproject.view.base.BaseViewModel
import com.log.JFLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.inject

const val METHOD = 2

class DbViewModel : BaseViewModel() {

    private val userDao: UserDao by inject()

    private val mEditLayoutErrorMessage = MutableLiveData<String>()
    val editLayoutErrorMessage: LiveData<String> = mEditLayoutErrorMessage

    private fun insertUserFlow(users: List<User>) = flow {
        JFLog.d("insertUserFlow start")
        userDao.insertUser(users)
        emit(DbResult.success("insertUserFlow"))
    }

    private fun insertOrReplaceUserFlow(users: List<User>) = flow {
        JFLog.d("insertOrReplaceUserFlow start")
        userDao.insertOrReplaceUser(users)
        emit(DbResult.success("insertOrReplaceUserFlow"))
    }

    fun insertUsers(users: List<User>) {
        viewModelScope.launch {
            insertUserFlow(users)
                .flatMapConcat {
                    when (it) {
                        is DbResult.Success -> {
                            insertOrReplaceUserFlow(users)
                        }
                        else -> {
                            throw RuntimeException("insertUsers fail")
                        }
                    }
                }
                .retryWhen { cause, attempt ->
                    JFLog.d("retryWhen attempt: $attempt")
                    attempt < 3
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(DbResult.error(e))
                }
                .onStart {
                    emit(DbResult.loading())
                }
                .onCompletion {
                    emit(DbResult.loaded())
                }
                .collect {
                    when (it) {
                        is DbResult.Success -> {
                            JFLog.i("Db: $it")
                        }
                        is DbResult.Error -> {
                            JFLog.e("Db: $it")
                        }
                        else -> {
                            JFLog.w("Db: $it")
                        }
                    }
                }
        }
    }

    fun queryUserByName(name: String) {
        when {
            name.isBlank() -> mEditLayoutErrorMessage.value = "Please input something"
            else -> {
                mEditLayoutErrorMessage.value = ""

                when (METHOD) {
                    1 -> queryUserByName1(name)
                    2 -> queryUserByName2(name)
                }
            }
        }
    }

    fun queryUserLikeName(name: String) {
        when {
            name.isBlank() -> mEditLayoutErrorMessage.value = "Please input something"
            else -> {
                mEditLayoutErrorMessage.value = ""

                when (METHOD) {
                    1 -> queryUserLikeName1(name)
                    2 -> queryUserLikeName2(name)
                }
            }
        }
    }

    /**
     * 方法一: Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
     */

    // Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
    fun queryUsers() {
        viewModelScope.launch {
            userDao.queryUsersFlow()
                .flowOn(Dispatchers.IO)
                .map {
                    DbResult.success(it)
                }
                .catch { e ->
                    emit(DbResult.error(e))
                }
                .onStart {
                    emit(DbResult.loading())
                }
                .onCompletion {
                    emit(DbResult.loaded())
                }
                .collect {
                    collectListResult(it)
                }
        }
    }

    // Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
    fun queryUserByName1(name: String) {
        viewModelScope.launch {
            userDao.queryUserByNameFlow(name)
                .flowOn(Dispatchers.IO)
                .map {
                    DbResult.success(it)
                }
                .catch { e ->
                    emit(DbResult.error(e))
                }
                .onStart {
                    emit(DbResult.loading())
                }
                .onCompletion {
                    emit(DbResult.loaded())
                }
                .collect {
                    collectResult(it)
                }
        }
    }

    // Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
    fun queryUserLikeName1(name: String) {
        viewModelScope.launch {
            userDao.queryUserLikeNameFlow(name)
                .flowOn(Dispatchers.IO)
                .map {
                    DbResult.success(it)
                }
                .catch { e ->
                    emit(DbResult.error(e))
                }
                .onStart {
                    emit(DbResult.loading())
                }
                .onCompletion {
                    emit(DbResult.loaded())
                }
                .collect {
                    collectResult(it)
                }
        }
    }

    /**
     * 方法二: 用Flow包裝結果
     */

    private fun queryUsersFlow() = flow {
        userDao.queryUsers().also {
            emit(DbResult.success(it))
        }
    }

    // 用Flow包裝結果
    fun queryUsers2() {
        viewModelScope.launch {
            queryUsersFlow()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(DbResult.error(e))
                }
                .onStart {
                    emit(DbResult.loading())
                }
                .onCompletion {
                    emit(DbResult.loaded())
                }
                .collect {
                    collectListResult(it)
                }
        }
    }

    private fun queryUserByNameFlow(name: String) = flow {
        userDao.queryUserByName(name).also {
            emit(DbResult.success(it))
        }
    }

    // 用Flow包裝結果
    fun queryUserByName2(name: String) {
        viewModelScope.launch {
            queryUserByNameFlow(name)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(DbResult.error(e))
                }
                .onStart {
                    emit(DbResult.loading())
                }
                .onCompletion {
                    emit(DbResult.loaded())
                }
                .collect {
                    collectResult(it)
                }
        }
    }

    private fun queryUserLikeNameFlow(name: String) = flow {
        userDao.queryUserLikeName(name).also {
            emit(DbResult.success(it))
        }
    }

    // 用Flow包裝結果
    fun queryUserLikeName2(name: String) {
        viewModelScope.launch {
            queryUserLikeNameFlow(name)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(DbResult.error(e))
                }
                .onStart {
                    emit(DbResult.loading())
                }
                .onCompletion {
                    emit(DbResult.loaded())
                }
                .collect {
                    collectResult(it)
                }
        }
    }

    private fun collectResult(result: DbResult<User>) {
        when (result) {
            is DbResult.Empty -> {
                mEditLayoutErrorMessage.value = "Db result empty"
                JFLog.d("Query fail")
            }
            is DbResult.Success -> {
                mEditLayoutErrorMessage.value = ""
                JFLog.d("${result.result}")
            }
            is DbResult.Error -> {
                mEditLayoutErrorMessage.value = "Db result error"
                JFLog.e("Db: $result")
            }
            else -> {
                JFLog.w("Db: $result")
            }
        }
    }

    private fun collectListResult(result: DbResult<List<User>>) {
        when (result) {
            is DbResult.Empty -> {
                mEditLayoutErrorMessage.value = "Db result empty"
                JFLog.d("Query fail")
            }
            is DbResult.Success -> {
                mEditLayoutErrorMessage.value = ""
                result.result.forEach { user ->
                    JFLog.d("Db: $user")
                }
            }
            is DbResult.Error -> {
                mEditLayoutErrorMessage.value = "Db result error"
                JFLog.e("Db: $result")
            }
            else -> {
                JFLog.w("Db: $result")
            }
        }
    }
}