package com.jeff.startproject.dao

import androidx.room.*
import com.jeff.startproject.model.db.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user")
    suspend fun nukeUserTable()

    @Insert
    suspend fun insertUser(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceUser(users: List<User>)

    @Query("SELECT * FROM user")
    suspend fun queryUsers(): List<User>

    @Query("SELECT * FROM user WHERE user_name = :name")
    suspend fun queryUserByName(name: String): User

    @Query("SELECT * FROM user WHERE user_name LIKE '%' || :name || '%'")
    suspend fun queryUserLikeName(name: String): User

    // 推薦: 回傳Flow物件
    @Query("SELECT * FROM user")
    fun queryUsersFlow(): Flow<List<User>>

    // 推薦: 回傳Flow物件
    @Query("SELECT * FROM user WHERE user_name = :name")
    fun queryUserByNameFlow(name: String): Flow<User>

    // 推薦: 回傳Flow物件
    @Query("SELECT * FROM user WHERE user_name LIKE '%' || :name || '%'")
    fun queryUserLikeNameFlow(name: String): Flow<User>
}