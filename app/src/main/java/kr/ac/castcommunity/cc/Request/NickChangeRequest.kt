import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response

import java.util.HashMap

class NickChangeRequest(
    id: String,
    nickname: String,
    confirm: String,
    listener: Response.Listener<String>
) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>

    companion object {
        // private val URL = "http://172.30.1.50/cc/nickchange.php"
        private val URL = "http://192.168.0.4/cc/nickchange.php"
        // private val URL = "http://192.168.219.103/cc/nickchange.php"

    }

    init {

        parameters = HashMap()
        parameters["id"] = id
        parameters["nickname"] = nickname
        parameters["confirm"] = confirm
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return parameters
    }

}
