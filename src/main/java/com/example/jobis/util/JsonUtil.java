package com.example.jobis.util;

import com.example.jobis.dto.Member;
import com.example.jobis.dto.jobis.SzsScrapResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtil {

    static final Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    public static Map<String,String> toMap(String json){
        Type type = new TypeToken<Map<String,String>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public static Member toMember(String json){
        return gson.fromJson(json, Member.class);
    }

    public static SzsScrapResult toSzsScrap(String json){
        return gson.fromJson(json, SzsScrapResult.class);
    }

}
