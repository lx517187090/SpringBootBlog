package com.example.springbootblog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * 文件操作工具类
 *
 * @author 张代浩
 */
public class FileUtils {
    protected static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtend(String filename) {
        return getExtend(filename, "");
    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtend(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');

            if ((i > 0) && (i < (filename.length() - 1))) {
                return (filename.substring(i + 1)).toLowerCase();
            }
        }
        return defExt.toLowerCase();
    }

    /**
     * 获取文件名称[不含后缀名]
     *
     * @param
     * @return String
     */
    public static String getFilePrefix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
    }

    /**
     * 获取文件名称[不含后缀名]
     * 不去掉文件目录的空格
     *
     * @param
     * @return String
     */
    public static String getFilePrefix2(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
    }

    /**
     * 文件复制
     * 方法摘要：这里一句话描述方法的用途
     *
     * @param
     * @return void
     */
    public static void copyFile(String inputFile, String outputFile) throws FileNotFoundException {
        File sFile = new File(inputFile);
        File tFile = new File(outputFile);
        FileInputStream fis = new FileInputStream(sFile);
        FileOutputStream fos = new FileOutputStream(tFile);
        int temp = 0;
        byte[] buf = new byte[10240];
        try {
            while ((temp = fis.read(buf)) != -1) {
                fos.write(buf, 0, temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 删除指定的文件
     *
     * @param strFileName 指定绝对路径的文件名
     * @return 如果删除成功true否则false
     */
    public static boolean delete(String strFileName) {
        File fileDelete = new File(strFileName);

        if (!fileDelete.exists() || !fileDelete.isFile()) {
            LogUtil.info("错误: " + strFileName + "不存在!");
            return false;
        }

        LogUtil.info("--------成功删除文件---------" + strFileName);
        return fileDelete.delete();
    }

    /**
     * @param @param  fileName
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: encodingFileName 2015-11-26 huangzq add
     * @Description: 防止文件名中文乱码含有空格时%20
     */
    public static String encodingFileName(String fileName) {
        String returnFileName = "";
        try {
            returnFileName = URLEncoder.encode(fileName, "UTF-8");
            returnFileName = StringUtils.replace(returnFileName, "+", "%20");
            if (returnFileName.length() > 150) {
                returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
                returnFileName = StringUtils.replace(returnFileName, " ", "%20");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.info("Don't support this encoding ...");
        }
        return returnFileName;
    }

    public static byte[] getFileByte(String url) {
        FileInputStream fileis = null;
        ByteArrayOutputStream baos = null;
        try {
            try {
                fileis = new FileInputStream(url);
            } catch (FileNotFoundException var19) {
                String weburl = null;
                try {
                    weburl = FileUtils.class.getClassLoader().getResource("").toURI().getPath();
                    weburl = weburl.replace("WEB-INF/classes/", "");
                    weburl = weburl.replace("file:/", "");
                    String root = weburl + url;
                    fileis = new FileInputStream(root);
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException(e1);
                }
            }
            baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fileis.read(bytes)) > -1) {
                baos.write(bytes, 0, length);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e1) {
            logger.error(e1.getMessage(), e1);
        } finally {
            try {
                if (fileis != null) {
                    fileis.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        logger.error(fileis + "这个路径文件没有找到,请查询");
        return null;
    }
}
