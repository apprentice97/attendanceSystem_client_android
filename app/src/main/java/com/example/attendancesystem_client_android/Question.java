package com.example.attendancesystem_client_android;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class Question {
    public static void main(String[] args) {
        String json = "{'msg':'返回的数据','code':200,'list':[{'name':'张三','ID':1},{'name':'李四','ID':2}],'map':{'name':'Map数据','ID':'Map'}}";
        Map<String,Object> objectMap = JSON.parseObject(json,Map.class);
        Log.i("Json", "code:" + objectMap.get("code"));
        Log.i("Json", "msg:" + objectMap.get("msg"));
        List<Map<String, Object>> list = JSON.parseObject(String.valueOf(objectMap.get("list")), List.class);
        Log.i("Json", "list:" + list.toString());
        Map<String, Object> map = JSON.parseObject(String.valueOf(objectMap.get("map")), Map.class);
        Log.i("Json", "msg:" + map.toString());

//        Json: code:200
//        Json: msg:返回的数据
//        Json: list:[{"name":"张三","ID":1}, {"name":"李四","ID":2}]
//        Json: msg:{ID=Map, name=Map数据}
    }
}
