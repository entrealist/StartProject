package com.jeff.startproject.view.db

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

class DbViewModel : BaseViewModel() {

    private val userDao: UserDao by inject()

    val editLayoutErrorMessage = MutableLiveData<String>()

    private fun insertUserFlow(users: List<User>) = flow {
        JFLog.d("insertUserFlow start")
        userDao.insertUser(users)
        emit(DbResult.success("insertUserFlow"))
    }

    fun insertUsers(users: List<User>) {
        viewModelScope.launch {
            insertUserFlow(users)
                .flatMapConcat {
                    when (it) {
                        is DbResult.Success -> {
                            insertUserFlow(users)
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
                    when (it) {
                        is DbResult.Empty -> {
                            JFLog.d("Query fail")
                        }
                        is DbResult.Success -> {
                            it.result.forEach { user ->
                                JFLog.d("Db: $user")
                            }
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

    // Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
    fun queryUserByName(name: String) {
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
                    when (it) {
                        is DbResult.Empty -> {
                            JFLog.d("Query fail")
                        }
                        is DbResult.Success -> {
                            JFLog.d("${it.result}")
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

    // Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
    fun queryUserLikeName(name: String) {
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
                    when (it) {
                        is DbResult.Empty -> {
                            JFLog.d("Query fail")
                        }
                        is DbResult.Success -> {
                            JFLog.d("${it.result}")
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
                    when (it) {
                        is DbResult.Empty -> {
                            JFLog.d("Query fail")
                        }
                        is DbResult.Success -> {
                            it.result.forEach { user ->
                                JFLog.d("Db: $user")
                            }
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
                    when (it) {
                        is DbResult.Empty -> {
                            JFLog.d("Query fail")
                        }
                        is DbResult.Success -> {
                            JFLog.d("${it.result}")
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
                    when (it) {
                        is DbResult.Empty -> {
                            JFLog.d("Query fail")
                        }
                        is DbResult.Success -> {
                            JFLog.d("${it.result}")
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
}