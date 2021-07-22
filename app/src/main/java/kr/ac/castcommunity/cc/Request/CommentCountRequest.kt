package kr.ac.castcommunity.cc.Request

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap


class CommentCountRequest(boardid: Int, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("COMMENT ERROR", "Server Response FAIL: $error")
    }) {
    private val parameters: MutableMap<String, String>

    companion object {
        private val URL = "http://172.30.1.50/cc/commentcount.php"
        //private val URL = "http://192.168.0.4/cc/commentcount.php"
        //private val URL = "http://192.168.219.103/cc/commentcount.php"
    }

    init {
        parameters = HashMap()
        parameters["boardid"] = boardid.toString()
    }


    override fun getParams(): Map<String, String> {

        return parameters
    }

}
