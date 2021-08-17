package kr.ac.castcommunity.cc.Request

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.*


class MessageSendRequest(my_nick: String, other_nick : String, content : String, listener: Response.Listener<String>) :
    StringRequest(
        Request.Method.POST,
        URL,
        listener,
        Response.ErrorListener { error ->
            Log.d("ERROR", "Server Response Fail: $error")
        }) {
    private val parameters: MutableMap<String, String>

    companion object {
        private val URL = "http://myIP/cc/messagesend.php"
    }

    init {
        parameters = HashMap()
        parameters["my_nick"] = my_nick
        parameters["other_nick"] = other_nick
        parameters["content"] = content
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }

}
