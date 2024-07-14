package dduw.com.mobile.finalreport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.databinding.ActivityIntroduceBinding

class IntroduceActivity: AppCompatActivity() {

    val binding by lazy {
        ActivityIntroduceBinding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }


}