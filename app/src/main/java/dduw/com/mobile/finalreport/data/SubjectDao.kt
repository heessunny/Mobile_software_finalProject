package dduw.com.mobile.finalreport.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns

class SubjectDao(val context: Context) {

    fun deleteSubject(subject: Subject) : Int {
        val helper = SubjectDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(subject.id.toString())

        return db.delete(SubjectDBHelper.TABLE_NAME, whereClause, whereArgs)
    }

    fun addSubject(subjects : Subject) : Long {
        val helper = SubjectDBHelper(context)
        val db = helper.writableDatabase

        val newValue = ContentValues()
        newValue.put(SubjectDBHelper.COL_NAME, subjects.name)
        newValue.put(SubjectDBHelper.COL_PROFESSOR, subjects.professor)
        newValue.put(SubjectDBHelper.COL_DIVISION, subjects.division)
        newValue.put(SubjectDBHelper.COL_LECTURE_ROOM, subjects.lecture_room)
        newValue.put(SubjectDBHelper.COL_COVER_IMG,subjects.coverImg)

        val newCount = db.insert(SubjectDBHelper.TABLE_NAME, null, newValue)

        helper.close()

        return newCount
    }

    fun updateFood(subjects : Subject): Int {
        val helper = SubjectDBHelper(context)
        val db = helper.writableDatabase

        val updateValue = ContentValues()
        updateValue.put(SubjectDBHelper.COL_NAME, subjects.name)
        updateValue.put(SubjectDBHelper.COL_PROFESSOR, subjects.professor)
        updateValue.put(SubjectDBHelper.COL_DIVISION, subjects.division)
        updateValue.put(SubjectDBHelper.COL_LECTURE_ROOM, subjects.lecture_room)
        updateValue.put(SubjectDBHelper.COL_COVER_IMG,subjects.coverImg)

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(subjects.id.toString())

        val resultCount =  db.update(SubjectDBHelper.TABLE_NAME,
            updateValue, whereClause, whereArgs)

        helper.close()
        return resultCount
    }

    @SuppressLint("Range")
    fun getAllSubjects() : ArrayList<Subject> {
        val helper = SubjectDBHelper(context)
        val db = helper.readableDatabase
        val cursor = db.query(SubjectDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val subjects = arrayListOf<Subject>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val name = getString(getColumnIndex(SubjectDBHelper.COL_NAME))
                val professor = getString(getColumnIndex(SubjectDBHelper.COL_PROFESSOR))
                val division = getInt(getColumnIndex(SubjectDBHelper.COL_DIVISION))
                val lectureRoom = getString(getColumnIndex(SubjectDBHelper.COL_LECTURE_ROOM))
                val coverImg = getInt(getColumnIndex(SubjectDBHelper.COL_COVER_IMG))
                val subject = Subject(id, name, professor, division, lectureRoom,coverImg )
                subjects.add(subject)
            }
        }
        cursor.close()
        return subjects
    }
}