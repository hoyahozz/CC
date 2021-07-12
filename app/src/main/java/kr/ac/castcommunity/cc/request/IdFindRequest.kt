package kr.ac.castcommunity.cc.request

import android.util.Log
import android.widget.EditText
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class IdFindRequest(email: String, nickname: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val map: MutableMap<String, String>
    companion object {
        private val URL = "http://192.168.100.249/idfind.php"
    }

    init {
        map = HashMap()
        map["email"] = email
        map["nickname"] = nickname

    }
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return map
    }
}