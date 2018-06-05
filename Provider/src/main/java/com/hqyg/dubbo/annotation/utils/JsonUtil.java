package com.hqyg.dubbo.annotation.utils;

import org.apache.commons.lang3.*;
import com.alibaba.fastjson.*;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil
{
    public static String toJson(final Object object) {
        return JSON.toJSONString(object, new SerializerFeature[] { SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNonStringKeyAsString });
    }
    
    public static String toJsonFormat(final Object object) {
        return JSON.toJSONString(object, new SerializerFeature[] { SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNonStringKeyAsString });
    }
    
    public static Object toJsonObject(final Object obj) {
        return JSON.toJSON(obj);
    }
    
    public static <T> T fromJson(final String jsonString, final Class<T> clazz) {
        if (StringUtils.isEmpty((CharSequence)jsonString)) {
            return null;
        }
        return (T)JSON.parseObject(jsonString, (Class)clazz);
    }
    
    public static <T> T fromJson(final String jsonString) throws Exception {
        return (T)JSON.parseObject(jsonString, (TypeReference)new TypeReference() {}, new Feature[0]);
    }
}