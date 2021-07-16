

package kr.ac.castcommunity.cc.request

import android.provider.ContactsContract
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class RegisterRequest(id: String, password: String, name: String, nickname:String, email:String,
                      role:String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, URL, listener, Response.ErrorListener { error ->
        Log.d("ERROR", "서버 Response 가져오기 실패: $error")
    }) {
    private val map: MutableMap<String, String>

    companion object {
        // const val URL = "http://192.168.100.251/cc/register.php"
        const val URL = "http://192.168.0.4/cc/register.php"
    }

    init {

        map = HashMap()
        map["id"] = id
        map["password"] = password
        map["name"] = name
        map["nickname"] = nickname
        map["email"] = email
        map["role"] = role
        Log.d("RegisterRequest","START")

    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return map
    }

}
