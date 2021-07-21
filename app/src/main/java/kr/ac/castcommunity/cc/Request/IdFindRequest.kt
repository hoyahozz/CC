package kr.ac.castcommunity.cc.Request

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class IdFindRequest(name: String, email: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val map: MutableMap<String, String>

    companion object {
        //private val URL = "http://192.168.1.93/cc/pwfind.php"
        //private val URL = "http://192.168.0.4/cc/idfind.php"
        private val URL = "http://192.168.219.103/cc/idfind.php"
        //private val URL = "http://172.30.1.31/cc/idfind.php"
        //private val URL = "http://192.168.100.249/idfind.php"
    }

    init {
        map = HashMap()
        map["name"] = name
        map["email"] = email
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return map
    }
}