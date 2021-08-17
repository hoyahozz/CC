package kr.ac.castcommunity.cc.Request

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.*

class PwFindRequest(id: String, email: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>

    companion object {
        private val URL = "http://myIP/cc/pwfind.php"
    }

    init {

        parameters = HashMap()
        parameters["id"] = id
        parameters["email"] = email
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return parameters
    }
}
