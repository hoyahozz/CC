package kr.ac.castcommunity.cc.request


import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.success
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class DeleteRequest(id: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>
    companion object {
        private val URL = "http://192.168.1.93/cc/userdelete.php"
        //private val URL = "http://172.30.1.31/cc/userdelete.php"
        // private val URL = "http://192.168.100.251/cc/userdelete.php"
        //private val URL = "http://192.168.100.249/cc/userdelete.php"
        // private val URL = "http://192.168.0.4/cc/boardwrite.php"
    }

    init {
        parameters = HashMap()
        parameters["id"] = id
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }


}
