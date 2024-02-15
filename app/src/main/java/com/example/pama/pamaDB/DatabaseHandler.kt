package com.example.pama.pamaDB


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.mindrot.jbcrypt.BCrypt


private const val dbname = "UserDb"
private const val tbname = "Users"
private const val col_username = "username"
private const val col_pass = "password"
private const val col_passwordHash = "passwordHash"
private const val col_id = "id"

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, dbname, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $tbname ($col_id INTEGER PRIMARY KEY AUTOINCREMENT,$col_username  TEXT,$col_pass  TEXT)"

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $tbname")
        onCreate(db)
    }





    fun insertData(user: Users): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(col_username, user.username)
        cv.put(col_pass, user.password)

        val result = db.insert(tbname, null, cv)
        return result != (-1).toLong()


    }


    @SuppressLint("Range")
    fun checkUserPass(username: String, password: String): Boolean {
        val db = writableDatabase

        // Use parameterized query to prevent SQL injection:

        val query = "SELECT * FROM $tbname WHERE $col_username = ? "
        val selectionArgs = arrayOf(username) // Store hash here instead of plain password

        val cursor = db.rawQuery(query, selectionArgs)

        return if (cursor.count<=0) {
            // User found, but don't return true directly
            val storedHash = cursor.getString(cursor.getColumnIndex(col_passwordHash))
            cursor.close()
            verifyPassword(password, storedHash) // Authenticate against stored hash
        } else {
            cursor.close()
            false // User not found or invalid password
        }
    }

    // Store hashed passwords securely (don't store plain text passwords):

    // Compare entered password with stored hash safely:
    private fun verifyPassword(password: String, storedHash: String): Boolean {
        // Ensure the stored hash is in the correct format (BCrypt usually starts with $2a$)
        return if (storedHash.startsWith("$2a$")) {
            BCrypt.checkpw(password.toCharArray().toString(), storedHash)
        } else {
            // Handle invalid hash format (e.g., log an error and return false)
            Log.e("DatabaseHandler", "Invalid password hash format")
            false
        }
    }

    fun userExists(username: String): Boolean {
        // Use parameterized query for security
        val query = "SELECT COUNT(*) FROM $tbname WHERE username = ?"

        val selectionArgs = arrayOf(username)

        val db = readableDatabase
        val cursor = db.rawQuery(query, selectionArgs)


        return if (cursor.moveToFirst()) {
            cursor.getInt(0) == 1 // Check if the returned count is 1 (user exists)
        } else {
            false // User not found
        } finally {
            cursor.close() // Always close the cursor
        }
    }




}

private infix fun Boolean.finally(any: Any): Boolean {
return true
}









