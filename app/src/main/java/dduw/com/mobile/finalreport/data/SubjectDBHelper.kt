package dduw.com.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import dduw.com.mobile.finalreport.R


class SubjectDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "SubjectDBHelper"

    companion object {
        const val DB_NAME = "subjects.db"
        const val TABLE_NAME = "SubjectTable"
        const val COL_NAME = "name"
        const val COL_PROFESSOR = "professor"
        const val COL_DIVISION = "division"
        const val COL_LECTURE_ROOM = "lecture_room"
        const val COL_COVER_IMG ="cover_Img"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE =      "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME TEXT, " +
                "$COL_PROFESSOR TEXT, " +
                "$COL_DIVISION INTEGER, " +
                "$COL_LECTURE_ROOM TEXT," +
                "$COL_COVER_IMG INTEGER)"
        Log.d(TAG, CREATE_TABLE)    // SQL 문장이 이상 없는지 Log에서 확인 필요
        db?.execSQL(CREATE_TABLE)

        db?.execSQL("INSERT INTO ${SubjectDBHelper.TABLE_NAME} VALUES (NULL, '자료구조', '박수희','01','인A301','${R.drawable.c}')")
        db?.execSQL("INSERT INTO ${SubjectDBHelper.TABLE_NAME} VALUES (NULL, '데이터베이스개론', '박창섭','02','인A302','${R.drawable.sql}')")
        db?.execSQL("INSERT INTO ${SubjectDBHelper.TABLE_NAME} VALUES (NULL, '운영체제', '이완연','01','인B303','${R.drawable.linux}')")
        db?.execSQL("INSERT INTO ${SubjectDBHelper.TABLE_NAME} VALUES (NULL, '네트워크', '임성채','02','인A301','${R.drawable.theory}')")
        db?.execSQL("INSERT INTO ${SubjectDBHelper.TABLE_NAME} VALUES (NULL, '모바일소프트웨어', '최윤철','01','인B301','${R.mipmap.ic_launcher}')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS ${SubjectDBHelper.TABLE_NAME}"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}