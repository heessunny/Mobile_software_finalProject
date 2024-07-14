package dduw.com.mobile.finalreport

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.data.Subject
import dduw.com.mobile.finalreport.data.SubjectDao
import dduw.com.mobile.finalreport.databinding.ActivityAddBinding

class AddActivity:AppCompatActivity() {

    val addBinding by lazy {
        ActivityAddBinding.inflate(layoutInflater)
    }
    val subjectDao by lazy {
        SubjectDao(this)
    }

        private var coverImgText: String =""

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContentView(addBinding.root)

            val radioGroupButtonChangeListener = RadioGroup.OnCheckedChangeListener { radioGroup, checkedId ->
                coverImgText = when (checkedId) {
                    addBinding.AddradioButtonC.id -> R.drawable.c.toString()
                    addBinding.AddradioButtonJava.id -> R.drawable.java.toString()
                    addBinding.AddradioButtonData.id -> R.drawable.sql.toString()
                    addBinding.AddradioButtonLinux.id -> R.drawable.linux.toString()
                    addBinding.AddradioButtonTheory.id -> R.drawable.theory.toString()
                    addBinding.AddradioButtonAndroid.id -> R.mipmap.ic_launcher.toString()
                    else -> R.drawable.ic_launcher_foreground.toString()
                }
            }

            addBinding.radiogroupAdd.setOnCheckedChangeListener(radioGroupButtonChangeListener)

            addBinding.btnAddSubject.setOnClickListener {
                val subName = addBinding.etAddSubName.text.toString()
                val professor = addBinding.etAddProfessor.text.toString()
                val divisionText = addBinding.etAddDivision.text.toString()
                val lectureRoom = addBinding.etAddLectureRoom.text.toString()

                val division = divisionText.toIntOrNull() ?: 0
                val coverImg = coverImgText?.toIntOrNull() ?: 0

                if (subName.isEmpty() || professor.isEmpty() || divisionText.isEmpty() || lectureRoom.isEmpty()||coverImgText.isEmpty()) {
                    Toast.makeText(this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    if (subjectDao.addSubject(Subject(0, subName, professor, division, lectureRoom, coverImg)) > 0) {
                        setResult(RESULT_OK)
                    } else {
                        setResult(RESULT_CANCELED)
                    }
                    finish()
                }
            }

            addBinding.btnAddCancel.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }

        }
    }