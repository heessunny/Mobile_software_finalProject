//과제명: 과목 위시리스트
//분반: 01 분반
//학번: 20210778 성명: 김희선
//제출일: 2024년 6월 25일

package dduw.com.mobile.finalreport

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dduw.com.mobile.finalreport.data.Subject
import dduw.com.mobile.finalreport.data.SubjectDao
import dduw.com.mobile.finalreport.databinding.ActivityMainBinding
import java.io.Serializable
class MainActivity : AppCompatActivity() {

    val REQ_ADD = 100
    val REQ_UPDATE = 200

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var subjects: ArrayList<Subject>
    lateinit var adapter: SubjectRVAdapter
    val subjectDao by lazy {
        SubjectDao(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvMain.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        subjects = subjectDao.getAllSubjects()
        adapter = SubjectRVAdapter(subjects)

        binding.rvMain.adapter = adapter

        adapter.setOnItemClickListener(object : SubjectRVAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("subject", subjects[position] as Serializable)
                startActivityForResult(intent, REQ_UPDATE)
            }
        })

        adapter.setOnItemLongClickListener(object : SubjectRVAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("과목 삭제")
                    setMessage("${subjects[position].name}을 삭제하시겠습니다?")
                    setPositiveButton("확인") { dialogInterface: DialogInterface?, i: Int ->
                        if (subjectDao.deleteSubject(subjects[position]) > 0) {
                            subjects.clear()
                            subjects.addAll(subjectDao.getAllSubjects())
                            adapter.filter("")  // Reset filter
                        }
                    }
                    setNegativeButton("취소", null)
                }
                builder.show()
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty())
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.menu_add -> {
                val intent= Intent(this, AddActivity::class.java)
                startActivityForResult(intent, REQ_ADD)
            }
            R.id.menu_introduce -> {
                val intent= Intent(this, IntroduceActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_close -> {
                val builder: AlertDialog.Builder= AlertDialog.Builder(this).apply {
                    setTitle("앱 종료")
                    setMessage("앱을 종료하시겠습니다?")
                    setPositiveButton("종료") { dialogInterface: DialogInterface?, i: Int ->
                        finish()
                    }
                    setNegativeButton("취소", null)
                }
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_UPDATE -> {
                if (resultCode == RESULT_OK) {
                    subjects.clear()
                    subjects.addAll(subjectDao.getAllSubjects())
                    adapter.filter("")
                }
            }
            REQ_ADD -> {
                if (resultCode == RESULT_OK) {
                    subjects.clear()
                    subjects.addAll(subjectDao.getAllSubjects())
                    adapter.filter("")
                }
            }
        }
    }
}