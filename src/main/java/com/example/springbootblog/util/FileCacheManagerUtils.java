package com.example.springbootblog.util;




import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import java.util.concurrent.TimeUnit;

/**
 * 文件缓存管理类
 * Created by zhouj on 2017/6/19.
 */
public class FileCacheManagerUtils {
    protected  static final Logger logger = LoggerFactory.getLogger(FileCacheManagerUtils.class);
    private static LoadingCache loadingCache;

    public static InputStream getFile(String url) {
        try {
            byte[] e = Arrays.copyOf((byte[])loadingCache.get(url), ((byte[])loadingCache.get(url)).length);
            return new ByteArrayInputStream(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    static {
        loadingCache = CacheBuilder.newBuilder().expireAfterWrite(7L, TimeUnit.DAYS).maximumSize(50L).build(new CacheLoader<String, byte[]>() {
            public byte[] load(String url) throws Exception {
                return FileUtils.getFileByte(url);
            }
        });
    }
}
