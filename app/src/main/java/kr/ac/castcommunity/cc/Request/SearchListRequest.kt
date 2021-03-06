package kr.ac.castcommunity.cc.Request

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap


class SearchListRequest(query: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "Server Response FAIL: $error")
    }) {
    private val parameters: MutableMap<String, String>

    companion object {
        private val URL = "http://myIP/cc/searchlist.php"
    }

    init {
        parameters = HashMap()
        parameters["query"] = query
    }


    override fun getParams(): Map<String, String> {

        return parameters
    }

}
