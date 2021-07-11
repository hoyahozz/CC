import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response

import java.util.HashMap

class LoginRequest(id: String, password: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, LoginRequest.URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val parameters: MutableMap<String, String>

    companion object {
        //  private val URL = "http://192.168.100.251/cc/login.php"
        private val URL = "http://192.168.0.4/cc/login.php"

    }

    init {

        parameters = HashMap()
        parameters["id"] = id
        parameters["password"] = password
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return parameters
    }

}
