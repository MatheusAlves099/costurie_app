package br.senai.sp.jandira.costurie_app.sqlite_repository

import android.content.Context
import br.senai.sp.jandira.costurie_app.dao.CosturieDb
import br.senai.sp.jandira.costurie_app.models_private.User

class UserRepositorySqlite(context: Context) {

    private val db = CosturieDb.getDatabase(context)

    fun save(user: User): Long {
        return db.userDao().save(user)
    }

    fun update(user: User): Long{
        return db.userDao().update(user).toLong()
    }

    fun findUserByEmail(id: Int): User{
        return db.userDao().findUserById(id)
    }

    fun findUsers(): List<User> {
        return db.userDao().findUsers()
    }

    fun deleteUser(): Int{
        return  db.userDao().deleteAll()
    }

    fun deleteAllUser(): Int{
        return  db.userDao().deleteAll()
    }

    fun updateIdChat(idChat: String): Int{
        return db.userDao().updateIdChat(idChat)
    }

    fun findIdChat(idChat: String): User{
        return db.userDao().findIdChat(idChat)
    }

}