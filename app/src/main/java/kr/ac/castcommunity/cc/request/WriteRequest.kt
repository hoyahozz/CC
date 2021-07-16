package kr.ac.castcommunity.cc.request


import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.success
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

<<<<<<< HEAD
<<<<<<< HEAD
class WriteRequest(id : String, title: String, content: String, writer : String, listener: Response.Listener<String>) :
=======
class WriteRequest(title: String, content: String, listener: Response.Listener<String>) :
>>>>>>> parent of 65318ca (merged)
=======
class WriteRequest(title: String, content: String, listener: Response.Listener<String>) :
>>>>>>> parent of 65318ca (merged)
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>
    companion object {
<<<<<<< HEAD
<<<<<<< HEAD
        // private val URL = "http://192.168.100.251/cc/boardwrite.php"
        private val URL = "http://192.168.0.4/cc/boardwrite.php"
=======
        private val URL = "http://192.168.100.251/cc/boardwrite2.php"
>>>>>>> parent of 65318ca (merged)
=======
        private val URL = "http://192.168.100.251/cc/boardwrite2.php"
>>>>>>> parent of 65318ca (merged)
    }

    init {
        parameters = HashMap()
        parameters["id"] = id
        parameters["title"] = title
        parameters["content"] = content
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }


}
