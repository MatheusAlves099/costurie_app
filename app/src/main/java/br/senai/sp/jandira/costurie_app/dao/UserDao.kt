package br.senai.sp.jandira.costurie_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.senai.sp.jandira.costurie_app.models_private.User

@Dao
interface UserDao {

    @Insert
    fun save(user: User): Long

    @Update
    fun update(user: User): Int

    @Query("DELETE FROM tbl_user WHERE id = :id")
    fun delete(id: Int): Int

    @Query("DELETE FROM tbl_user")
    fun deleteAll(): Int

    @Query("SELECT * FROM tbl_user WHERE id = :id")
    fun findUserById(id: Int): User

    @Query("SELECT * FROM tbl_user")
    fun findUsers(): List<User>

    @Query("UPDATE tbl_user set idChat = :idChat")
    fun updateIdChat(idChat: String): Int
    @Query("SELECT * FROM tbl_user WHERE idChat = :idChat")
    fun findIdChat(idChat: String): User
}