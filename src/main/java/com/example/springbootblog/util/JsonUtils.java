package com.example.springbootblog.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by zhouj on 2017/9/6.
 */
public final class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        //mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    public static <T> T parseObject(final String json, Class<T> clazz) {
        if (null == json || "".equals(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param content json字符串
     * @return JsonNode
     * <p>
     * zhoul
     */
    public static JsonNode getJsonNode(String content) {
        if (null == content || "".equals(content)) {
            return null;
        }
        return getJsonNode(content.getBytes());
    }

    /**
     * @param content json字符串字节码
     * @return JsonNode
     * <p>
     * zhoul
     */
    public static JsonNode getJsonNode(byte[] content) {
        try {
            return mapper.readTree(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map json2Map(final String json) {
        return parseObject(json, Map.class);
    }

    public static <T> List<T> json2List(final String json) {

        if (null == json || "".equals(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static String toJSONString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("对象转json 格式化时间异常：" + e);
        }
        return null;
    }
}
