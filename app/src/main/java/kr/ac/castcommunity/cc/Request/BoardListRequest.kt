package kr.ac.castcommunity.cc.Request

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.*


class BoardListRequest(btype: String, listener: Response.Listener<String>) :
    StringRequest(
        Request.Method.POST,
        URL,
        listener,
        Response.ErrorListener { error ->
            Log.d("ERROR", "Server Response Fail: $error")
        }) {
    private val parameters: MutableMap<String, String>

    companion object {
         private val URL = "http://myIP/cc/boardlist.php"
    }

    init {
        parameters = HashMap()
        parameters["btype"] = btype
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }

}
