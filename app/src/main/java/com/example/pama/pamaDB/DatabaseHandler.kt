    package com.example.pama.pamaDB


    import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
    import org.mindrot.jbcrypt.BCrypt


    private const val dbname = "UserDb"
    private const val tbname = "Users"
    private const val businesstbname = "Business"
    private const val col_businessname = "Business_name"
    private const val col_category = "Category"
    private const val col_location = "Location"
    private const val accounttbname = "Account"
    private const val col_wallet = "Wallet"
    private const val col_income = "Income"
    private const val col_expense = "Expense"
    private const val col_username = "username"
    private const val col_pass = "password"
    private const val col_passwordHash = "passwordHash"
    private const val col_id = "id"


    private const val transtbname="Transactions"
    private const val col_title = "Title"
    private const val col_cat = "Category"
    private const val col_wal = "Wallet"
    private const val col_date = "Date"
    private const val col_transtype = "TransactionType"
    private const val col_inc = "Income"
    private const val col_exp = "Expense"


    class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, dbname, null, 1) {


        override fun onCreate(db: SQLiteDatabase?) {
            val createTable =
                "CREATE TABLE $tbname ($col_id INTEGER PRIMARY KEY AUTOINCREMENT,$col_username  TEXT,$col_pass  TEXT, $col_passwordHash TEXT)"

            db?.execSQL(createTable)
            val createTableBusinesses =
                "CREATE TABLE $businesstbname (Business_id INTEGER PRIMARY KEY AUTOINCREMENT, $col_businessname TEXT, $col_category TEXT, $col_location TEXT)"
            db?.execSQL(createTableBusinesses)
            val createTableAccounts =
                "CREATE TABLE $accounttbname (Ac_id INTEGER PRIMARY KEY AUTOINCREMENT, $col_wallet TEXT, $col_income DOUBLE, $col_expense DOUBLE)"
            db?.execSQL(createTableAccounts)
            val createTableTransactions =
                "CREATE TABLE $transtbname (Trans_id INTEGER PRIMARY KEY AUTOINCREMENT, $col_title TEXT, $col_wal TEXT, $col_date TEXT, $col_transtype TEXT, $col_inc DOUBLE, $col_exp DOUBLE)"
            db?.execSQL(createTableTransactions)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $tbname")
            db?.execSQL("DROP TABLE IF EXISTS $businesstbname")
            db?.execSQL("DROP TABLE IF EXISTS $accounttbname")
            db?.execSQL("DROP TABLE IF EXISTS $transtbname")
            onCreate(db)
        }


        fun insertDataUsers(user: Users): Boolean {
            val db = writableDatabase

            val cvUsers = ContentValues()
            cvUsers.put(col_username, user.username)
            cvUsers.put(col_passwordHash, getHashedPassword(user.password))
            val result = db.insert(tbname, null, cvUsers)
            return result != (-1).toLong()
        }

        fun insertDataBusiness(businessname: String, category: String, location: String):Boolean {
            val db = writableDatabase
            //Business table
            val cvBusiness = ContentValues()
            cvBusiness.put(col_businessname, businessname)
            cvBusiness.put(col_category, category)
            cvBusiness.put(col_location, location)

            val result = db.insert(businesstbname, null, cvBusiness)
            return result != (-1).toLong()
        }
        fun insertDataAccount(account: Account):Boolean {
            val db = writableDatabase
            //Accounts table
            val cvAccount = ContentValues()
            cvAccount.put(col_wallet, account.wallet)
            cvAccount.put(col_income, account.income)
            cvAccount.put(col_expense, account.expense)

            val result = db.insert(accounttbname, null, cvAccount)
            return result != (-1).toLong()
        }

        fun insertDataTransactions(
            title: String,
            dateString: String,
            wallet: String,
            type: String,
            incomeAmount: Double,
            expenseAmount: Double
        ):Boolean {
            val db = writableDatabase
            //Accounts table
            val cvTransactions = ContentValues()
            cvTransactions.put(col_title, title)
            cvTransactions.put(col_date, dateString)
            cvTransactions.put(col_wal, wallet)
            cvTransactions.put(col_transtype, type)
            cvTransactions.put(col_inc, incomeAmount)
            cvTransactions.put(col_exp, expenseAmount)


            val result = db.insert(transtbname, null, cvTransactions)
            return result != (-1).toLong()
        }




        @SuppressLint("Range")
        fun checkUserPass(username: String, password: String): Pair<LoginStatus, String?> {
            val db = readableDatabase

            var status = LoginStatus.USER_NOT_FOUND

            val query = "SELECT $col_passwordHash FROM $tbname WHERE $col_username = ?"
            val selectionArgs = arrayOf(username)
            val cursor = db.rawQuery(query, selectionArgs)

            try {
                if (cursor.moveToFirst()) {
                    val storedHash = cursor.getString(cursor.getColumnIndex(col_passwordHash))
                    status = if (BCrypt.checkpw(password, storedHash)) {
                        LoginStatus.SUCCESS
                    } else {
                        LoginStatus.INVALID_PASSWORD
                    }
                }
            } finally {
                cursor.close()
            }

            if (status == LoginStatus.SUCCESS) {
                return Pair(status, null)
            }

            // If the user is not found, hash the user's input password and check if the user exists
            val user = Users(username, password, null)
            val userExists = checkUserExists(user)

            if (userExists) {
                status = LoginStatus.SUCCESS
            }

            return Pair(status, if (status == LoginStatus.SUCCESS) null else "Invalid username or password")
        }


        private fun checkUserExists(user: Users): Boolean {

            val db = readableDatabase

            val query = "SELECT COUNT(*) FROM $tbname WHERE $col_username = ?"
            val selectionArgs = arrayOf(user.username)
            val cursor = db.rawQuery(query, selectionArgs)

            try {
                if (cursor.moveToFirst()) {
                    val count = cursor.getInt(0)
                    return count > 0
                }
            } finally {
                cursor.close()
            }

            return false


        }

        // Store hashed passwords securely :

        // Compare entered password with stored hash safely:
      fun verifyPassword(password: String, storedHash: String): Boolean {
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

        fun checkUserExists(username: String): Boolean {
            val db = readableDatabase
            val query = "SELECT * FROM $tbname WHERE $col_username = ?"
            val selectionArgs = arrayOf(username)
            val cursor = db.rawQuery(query, selectionArgs)

            return cursor.moveToFirst()
        }


        //get user by user name
        @SuppressLint("Range")
        fun getUserByUsername(username: String): Users? {
            val db = writableDatabase

            // Use parameterized query to prevent SQL injection:
            val selectionArgs = arrayOf(username)
            val cursor = db.query(
                tbname,
                null,
                "$col_username = ?",
                selectionArgs,
                null,
                null,
                null
            )

            return if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(col_id))
                val storedPasswordHash = cursor.getString(cursor.getColumnIndex(col_passwordHash))
                Users(id.toString(), username, storedPasswordHash)
            } else {
                null // User not found
            }
        }



        //read the Business table
        fun getBusiness(): Cursor? {
            val db = this.writableDatabase
            return db.rawQuery("SELECT * FROM $businesstbname", null)

        }

        @SuppressLint("Range")
        fun getPasswordHash(username: String): String? {
            val db = readableDatabase
            val query = "SELECT $col_passwordHash FROM $tbname WHERE $col_username = ?"
            val selectionArgs = arrayOf(username)
            return db.rawQuery(query, selectionArgs).use { cursor ->
                if (cursor.moveToFirst()) {
                    cursor.getString(cursor.getColumnIndex(col_passwordHash))
                } else {
                    null
                }
            }
        }


        //hashes the password we entered
        fun getHashedPassword(plainTextPassword: String): String {

            return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt())

        }



        //Read incomes and expenses from the database using transactions table
        fun getTotalIncomeByAccount(accountWallet: String): Double {
            val db = readableDatabase
            val query = "SELECT SUM($col_inc) FROM $transtbname WHERE $col_wal = ?"
            val selectionArgs = arrayOf(accountWallet)
            val cursor = db.rawQuery(query, selectionArgs)

            return if (cursor.moveToFirst()) {
                cursor.getDouble(0)
            } else {
                0.0
            }
        }

        fun getTotalExpenseByAccount(accountWallet: String): Double {
            val db = readableDatabase
            val query = "SELECT SUM($col_exp) FROM $transtbname WHERE $col_wal = ?"
            val selectionArgs = arrayOf(accountWallet)
            val cursor = db.rawQuery(query, selectionArgs)

            return if (cursor.moveToFirst()) {
                cursor.getDouble(0)
            } else {
                0.0
            }
        }


        //handles diplaying total amount in the home page
        fun getBalanceByWalletType(walletType: String): Double {
            val db = readableDatabase
            val query = "SELECT SUM($col_inc) - SUM($col_exp) FROM $transtbname WHERE $col_wal = ?"
            val selectionArgs = arrayOf(walletType)
            val cursor = db.rawQuery(query, selectionArgs)

            return if (cursor.moveToFirst()) {
                cursor.getDouble(0)
            } else {
                0.0
            }
        }



    }



    private infix fun Boolean.finally(any: Any): Boolean {
    return true
    }









