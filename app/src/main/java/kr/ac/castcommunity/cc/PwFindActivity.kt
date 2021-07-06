package kr.ac.castcommunity.cc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import kr.ac.castcommunity.cc.Toolbar.FindToolbarActivity

class PwFindActivity : FindToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pwfind)

        val idFind = findViewById<Button>(R.id.findTo_id)

        idFind.setOnClickListener {
            val intent = Intent(applicationContext, IdFindActivity::class.java)
            startActivity(intent)
        }
    }
}
