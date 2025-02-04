package ru.zettatech.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.zettatech.category.Category
import ru.zettatech.constant.Constant
import java.util.HashMap

class DBHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, Constant.DATABASE, FACTORY, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        createTables(db)

        val dumpData = DumpData()
        dumpData.insertData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        private val FACTORY: SQLiteDatabase.CursorFactory? = null
        private val DATABASE_VERSION = 1

        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(context: Context): DBHelper? {
            if (instance == null) {
                instance = DBHelper(context.applicationContext)
            }

            return instance
        }
    }

    private fun createTables(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + Constant.CATEGORY + " (" +
                    Constant.CATEGORY_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                    Constant.PARENT_ID + " integer DEFAULT 0, " +
                    Constant.IMAGE + " text NOT NULL DEFAULT '', " +
                    Constant.TITLE + " text NOT NULL DEFAULT '', " +
                    Constant.DESCRIPTION + " text NOT NULL DEFAULT '', " +
                    Constant.SORT_ORDER + " integer DEFAULT 0, " +
                    Constant.STATUS + " integer DEFAULT 0, " +
                    Constant.DATE_ADDED + " text NOT NULL DEFAULT '', " +
                    Constant.DATE_MODIFICATION + " text NOT NULL DEFAULT ''" + ");"
        )
    }

    fun getCategories(filterData: HashMap<String, String>): ArrayList<Category> {
        val db = this.readableDatabase
        val arrayList: ArrayList<Category> = ArrayList()

        val sql = "SELECT " +
                Constant.CATEGORY_ID + ", " +
                Constant.PARENT_ID + ", " +
                Constant.IMAGE + ", " +
                Constant.TITLE + ", " +
                Constant.DESCRIPTION + ", " +
                Constant.SORT_ORDER + ", " +
                Constant.STATUS + ", " +
                Constant.DATE_ADDED + ", " +
                Constant.DATE_MODIFICATION + " " +
                " FROM " + Constant.CATEGORY +
                " WHERE " + Constant.PARENT_ID + " = ?"

        val selectionArgs: Array<String?> = arrayOf(filterData[Constant.PARENT_ID])

        val cursor = db.rawQuery(sql, selectionArgs)

        if (cursor.moveToFirst()) {
            for (i in 0 until cursor.count) {

                val category = Category(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Constant.CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constant.IMAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constant.TITLE))
                )

                arrayList.add(category)
                cursor.moveToNext()
            }

            cursor.close()
        }

        return arrayList
    }
}