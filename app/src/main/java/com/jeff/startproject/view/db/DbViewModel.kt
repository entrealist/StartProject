package com.jeff.startproject.view.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeff.startproject.dao.UserDao
import com.jeff.startproject.model.db.DbResult
import com.jeff.startproject.model.db.User
import com.log.JFLog
import com.utils.lifecycle.SingleEvent
import com.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class DbViewModel : BaseViewModel(), KoinComponent {

    companion object {
        private const val METHOD = 2
    }

    private val userDao: UserDao by inject()

    private val _dbSingleResult = MutableLiveData<SingleEvent<DbResult<User>>>()
    val dbSingleResult: LiveData<SingleEvent<DbResult<User>>> = _dbSingleResult

    private val _dbListResult = MutableLiveData<SingleEvent<DbResult<List<User>>>>()
    val dbListResult: LiveData<SingleEvent<DbResult<List<User>>>> = _dbListResult

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

    fun nukeTable() {
        viewModelScope.launch {
            userDao.nukeUserTable()
        }
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
                    emit(DbResult.failure(e))
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
                        is DbResult.Failure -> {
                            JFLog.e("Db: ${it.throwable}")
                        }
                        else -> {
                            JFLog.w("Db: $it")
                        }
                    }
                }
        }
    }

    fun queryUserList() {
        when (METHOD) {
            1 -> queryUsers1()
            2 -> queryUsers2()
        }
    }

    fun queryUserByName(name: String) {
        when {
            name.isBlank() -> _dbSingleResult.value =
                SingleEvent(DbResult.failure(RuntimeException("Please input something")))
            else -> {
                when (METHOD) {
                    1 -> queryUserByName1(name)
                    2 -> queryUserByName2(name)
                }
            }
        }
    }

    fun queryUserLikeName(name: String) {
        when {
            name.isBlank() -> _dbListResult.value =
                SingleEvent(DbResult.failure(RuntimeException("Please input something")))
            else -> {
                when (METHOD) {
                    1 -> queryUserLikeName1(name)
                    2 -> queryUserLikeName2(name)
                }
            }
        }
    }

    /**
     * 推薦
     * Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
     */
    private fun queryUsers1() {
        viewModelScope.launch {
            userDao.queryUsersFlow()
                .flowOn(Dispatchers.IO)
                .map {
                    val data =
                        if (it.isEmpty()) {
                            null
                        } else {
                            it
                        }

                    DbResult.success(data)
                }
                .catch { e ->
                    emit(DbResult.failure(e))
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

    /**
     * 推薦
     * Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
     */
    private fun queryUserByName1(name: String) {
        viewModelScope.launch {
            userDao.queryUserByNameFlow(name)
                .flowOn(Dispatchers.IO)
                .map {
                    DbResult.success(it)
                }
                .catch { e ->
                    emit(DbResult.failure(e))
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
     * 推薦
     * Dao回傳Flow, 不用再包裝, 透過map轉換成DBResult.
     */
    private fun queryUserLikeName1(name: String) {
        viewModelScope.launch {
            userDao.queryUserLikeNameFlow(name)
                .flowOn(Dispatchers.IO)
                .map {
                    DbResult.success(it)
                }
                .catch { e ->
                    emit(DbResult.failure(e))
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
     * 用Flow包裝結果
     */
    private val flowQueryUsers2 = flow {
        userDao.queryUsers().also {
            val data =
                if (it.isEmpty()) {
                    null
                } else {
                    it
                }

            emit(DbResult.success(data))
        }
    }

    private fun queryUsers2() {
        viewModelScope.launch {
            flowQueryUsers2
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(DbResult.failure(e))
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

    /**
     * 用Flow包裝結果
     */
    private fun queryUserByName2(name: String) {
        viewModelScope.launch {
            flow {
                userDao.queryUserByName(name).also {
                    emit(DbResult.success(it))
                }
            }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(DbResult.failure(e))
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

    private fun queryUserLikeName2(name: String) {
        viewModelScope.launch {
            flow {
                userDao.queryUserLikeName(name).also {
                    emit(DbResult.success(it))
                }
            }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(DbResult.failure(e))
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
        _dbSingleResult.value = SingleEvent(result)
    }

    private fun collectListResult(result: DbResult<List<User>>) {
        _dbListResult.value = SingleEvent(result)
    }
}