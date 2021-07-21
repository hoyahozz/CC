package kr.ac.castcommunity.cc

import android.os.Bundle
import kr.ac.castcommunity.cc.Toolbar.NoteToolbarActivity

class NoteSendActivity: NoteToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_send_toolbar)
    }
}

