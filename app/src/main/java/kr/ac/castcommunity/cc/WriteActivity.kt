package kr.ac.castcommunity.cc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.castcommunity.cc.Toolbar.WriteToolbarActivity

class WriteActivity : WriteToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write)
    }
}
