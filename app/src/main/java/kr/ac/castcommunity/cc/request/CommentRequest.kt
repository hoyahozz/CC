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


class CommentRequest(boardid : Int , listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL,listener, Response.ErrorListener { error ->
        Log.d("COMMENT ERROR", "Server Response FAIL: $error")
    }) {
    private val parameters: MutableMap<String, String>
    companion object {
        // private val URL = "http://192.168.100.251/cc/commentlist.php"
        private val URL = "http://192.168.0.4/cc/commentlist.php"
    }

    init {
        parameters = HashMap()
        parameters["boardid"] = boardid.toString()
    }



    override fun getParams(): Map<String, String> {

        return parameters
    }

}
