package dduw.com.mobile.finalreport.data

import java.io.Serializable


data class Subject(
    var id: Int,
    var name: String,
    var professor: String,
    var division: Int,
    var lecture_room: String,
    var coverImg: Int
) : Serializable
