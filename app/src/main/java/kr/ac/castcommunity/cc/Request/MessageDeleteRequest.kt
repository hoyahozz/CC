package kr.ac.castcommunity.cc.Request


import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class MessageDeleteRequest(messageroom: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>

    companion object {
        //private val URL = "http://192.168.100.251/cc/messagedelete.php"
        private val URL = "http://192.168.0.4/cc/messagedelete.php"
        //private val URL = "http://192.168.219.103/cc/messagedelete.php"
        //private val URL = "http://172.30.1.50/cc/messagedelete.php"
    }

    init {
        parameters = HashMap()
        parameters["messageroom"] = messageroom
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }


}
