package com.example.callivoice;

import com.google.android.gms.common.api.Response;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 13 디콘 김덕영 on 2018-04-07.
 */

public class RegisterRequest extends StringRequest {
    final static private String URL ="https://calli-voice.firebaseio.com/";
    private Map<String, String> parameters;

    public RegisterRequest(String idText, String passwordText, String emailText, Response.Listner<String> listner){
        super(Method.POST,URL, listner, null);
        parameters =new HashMap<>();
        parameters.put("idText",idText);
        parameters.put("passwordText",passwordText);
        parameters.put("emailText",emailText);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
