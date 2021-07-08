package kr.ac.castcommunity.cc.request

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.success
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import java.util.HashMap
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray


class BoardListRequest(listener: Response.Listener<JSONArray>) :
    JsonArrayRequest(Request.Method.POST, URL, JSONArray(), listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>
    companion object {
        private val URL = "http://192.168.100.251/cc/boardlist.php"
    }

    init {
        parameters = HashMap()
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }

}
