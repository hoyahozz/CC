package kr.ac.castcommunity.cc.Request


import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class CommentDeleteRequest(memId: String, commentid: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>

    companion object {
        //private val URL = "http://192.168.100.251/cc/commentdelete.php"
        // private val URL = "http://192.168.0.4/cc/commentdelete.php"
        private val URL = "http://192.168.219.103/cc/commentdelete.php"
    }

    init {
        parameters = HashMap()
        parameters["memId"] = memId
        parameters["commentid"] = commentid
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }


}
