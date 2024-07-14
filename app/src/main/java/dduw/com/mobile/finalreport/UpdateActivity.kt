package dduw.com.mobile.finalreport

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.data.Subject
import dduw.com.mobile.finalreport.data.SubjectDao
import dduw.com.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity:AppCompatActivity() {

    val updateBinding by lazy {
        ActivityUpdateBinding.inflate(layoutInflater)
    }
    val subjectDao by lazy {
        SubjectDao(this)
    }
    private var coverImgText: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(updateBinding.root)

        val subject = intent.getSerializableExtra("subject") as Subject


        // 초기 값 설정
        updateBinding.etUpdateId.setText(subject.id.toString())
        updateBinding.etUpdateSubName.setText(subject.name)
        updateBinding.etUpdateProfessor.setText(subject.professor)
        updateBinding.etUpdateDivision.setText(subject.division.toString())
        updateBinding.etUpdateLectureRoom.setText(subject.lecture_room)
        updateBinding.updateImgIv.setImageResource(subject.coverImg!!)

        val radioGroupButtonChangeListener = RadioGroup.OnCheckedChangeListener { radioGroup, checkedId ->
            coverImgText = when (checkedId) {
                updateBinding.UpdateradioButtonC.id -> R.drawable.c.toString()
                updateBinding.UpdateradioButtonJava.id -> R.drawable.java.toString()
                updateBinding.UpdateradioButtonData.id -> R.drawable.sql.toString()
                updateBinding.UpdateradioButtonLinux.id -> R.drawable.linux.toString()
                updateBinding.UpdateradioButtonTheory.id -> R.drawable.theory.toString()
                updateBinding.UpdateradioButtonAndroid.id -> R.mipmap.ic_launcher.toString()
                else -> R.drawable.ic_launcher_foreground.toString()
            }
        }

        updateBinding.radiogroupUpdate.setOnCheckedChangeListener(radioGroupButtonChangeListener)
        updateBinding.btnUpdateSubject.setOnClickListener {
            val SubName =updateBinding.etUpdateSubName.text.toString()
            val Professor =updateBinding.etUpdateProfessor.text.toString()
            val divisionText = updateBinding.etUpdateDivision.text.toString()
            val LectureRoom =updateBinding.etUpdateLectureRoom.text.toString()
            if(SubName.isEmpty()||Professor.isEmpty()||divisionText.isEmpty()||LectureRoom.isEmpty()||coverImgText.isEmpty()){

                Toast.makeText(this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                subject.name = SubName
                subject.professor = Professor
                subject.lecture_room = LectureRoom
                subject.division = divisionText.toIntOrNull() ?: 0
                subject.coverImg = coverImgText?.toIntOrNull() ?: 0

                if (subjectDao.updateFood(subject) > 0) {
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }

                finish()
            }

        }

        updateBinding.btnUpdateCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

}