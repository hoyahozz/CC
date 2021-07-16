package kr.ac.castcommunity.cc.request

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class ValidateRequest(id: String, listener: Response.Listener<String>):
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val map: MutableMap<String, String>

    companion object {
        private const val URL = "http://192.168.1.93/cc/validate.php"
        //private const val URL = "http://172.30.1.31/cc/validate.php"
        // private const val URL = "http://192.168.100.251/cc/validate.php"
        //private const val URL = "http://192.168.100.249/cc/validate.php"
    }
    init {
        map = HashMap()
        map["id"] = id
    }
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return map
    }
}