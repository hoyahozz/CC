package kr.ac.castcommunity.cc.request


import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.success
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class PwChangeRequest(id : String, pw : String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("DETAIL ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>
    companion object {
        private val URL = "http://192.168.100.251/cc/pwchange.php"
        // private val URL = "http://192.168.0.4/cc/pwchange.php"
    }

    init {
        parameters = HashMap()
        parameters["id"] = id
        parameters["pw"] = pw
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }


}
