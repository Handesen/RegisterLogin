package com.example.handesen.registerlogin;

/**
 * Created by Handesen on 2016. 10. 03..
 */



    import com.android.volley.Response;
    import com.android.volley.toolbox.StringRequest;

    import java.util.HashMap;
    import java.util.Map;

    public class RegisterRequest extends StringRequest {
        private static final String REGISTER_REQUEST_URL = "http://szakdolgozat.comxa.com/Register.php";

        private Map<String, String> params;

        public RegisterRequest(String name, String username, String email, String password, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("name", name);
            params.put("email", email);
            params.put("username", username);
            params.put("password", password);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
