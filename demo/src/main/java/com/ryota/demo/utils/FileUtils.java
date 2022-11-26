package com.ryota.demo.utils;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

/**
 * @Description TODO
 * @Date 2022/11/26 18:22
 * @Author ryota
 */
public class FileUtils {
    /**
     * @Description: 网络资源转file, 用完以后必须删除该临时文件
     * @author: 赵兴炎
     * @date: 2019年7月10日
     * @return: 返回值
     * @param fileUrl
     */
    public static File urlToFile(String fileUrl,String fileName) {
        String path = System.getProperty("user.dir");
        File upload = new File(path, "tmp");
        if (!upload.exists()) {
            upload.mkdirs();
        }
        return urlToFile(fileUrl, upload,fileName);
    }

    /**
     * @param fileUrl 资源地址
     * @param upload  临时文件路径
     * @Description: 网络资源转file, 用完以后必须删除该临时文件
     * @author: 赵兴炎
     * @date: 2019年7月10日
     * @return: 返回值
     */
    public static File urlToFile(String fileUrl, File upload,String fileName) {
//        String suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
//        String name = fileName;
        FileOutputStream downloadFile = null;
        InputStream openStream = null;
        File savedFile = null;
        try {
            savedFile = new File(upload.getAbsolutePath() + "/" + fileName );
            URL url = new URL(fileUrl);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            openStream = connection.getInputStream();
            int index;
            byte[] bytes = new byte[1024];
            downloadFile = new FileOutputStream(savedFile);
            while ((index = openStream.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (openStream != null) {
                    openStream.close();
                }
                if (downloadFile != null) {
                    downloadFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return savedFile;
    }


    public static MultipartFile getMultipartFile(String fileName, String fileUrl){
        File file = urlToFile(fileUrl,null);
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        FileItem item = factory.createItem(textFieldName, "text/plain", true, fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonsMultipartFile(item);
    }



    /**
     * 调用流程上传文件接口上传文件
     * @param filePath
     * @return
     */
    public static String sendPostUplodFile(String filePath,String fileName) {
        DataOutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
//            URL realUrl = new URL( "http://10.72.66.27:9090/oss/upload/csp/uploadMaterial");
            URL realUrl = new URL( "https://csp.cet.cnooc.com.cn/oss/upload/csp/uploadMaterial");
            HttpsUrlValidator.trustAllHttpsCertificates();
            //打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            //发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            String BOUNDARY = "----WebKitFormBoundary07I8UIuBx6LN2KyY";
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();

            out = new DataOutputStream(conn.getOutputStream());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            //添加参数
            StringBuffer sb1 = new StringBuffer();
            sb1.append("--");
            sb1.append(BOUNDARY);
            sb1.append("\r\n");
            sb1.append("Content-Disposition: form-data;name=\"luid\"");
            sb1.append("\r\n");
            sb1.append("\r\n");
            sb1.append("123");
            sb1.append("\r\n");
            out.write(sb1.toString().getBytes());
            //添加参数file
            File file = urlToFile(filePath,fileName);

            StringBuffer sb = new StringBuffer();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"");
            sb.append("\r\n");
            sb.append("Content-Type: application/octet-stream");
            sb.append("\r\n");
            sb.append("\r\n");
            out.write(sb.toString().getBytes());

            DataInputStream in1 = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in1.read(bufferOut)) != -1) {
                out.write(bufferOut,0,bytes);
            }
            out.write("\r\n".getBytes());
            in1.close();
            out.write(end_data);

            //flush输出流的缓冲
            out.flush();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * HttpsURLConnection上传文件流
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //本地图片
        java.io.File file = new java.io.File("C:/Users/ex_wangyw9/Desktop/84b6d04fe1124d619e24ee085e7d3ac1.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        //对接外部接口
        String urlString = "https://csp.cet.cnooc.com.cn/oss/upload/uploadMaterial";
        HttpsUrlValidator.trustAllHttpsCertificates();
        URL url = new URL(urlString);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        con.setDoOutput(true);
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        con.setDoInput(true);
        // 设定请求的方法为"POST"，默认是GET
        con.setRequestMethod("POST");
        // Post 请求不能使用缓存
        con.setUseCaches(false);
        String BOUNDARY = "----WebKitFormBoundary07I8UIuBx6LN2KyY";
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("connection", "Keep-Alive");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        con.setRequestProperty("Charsert", "UTF-8");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        con.connect();
        // 设定传送的内容类型是可序列化的java对象
        // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
        //con.setRequestProperty("Content-type", "application/x-java-serialized-object");
        OutputStream out = con.getOutputStream();

        //读取本地图片文件流
        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[2048];
        int len = 0;
        int sum = 0;
        while ((len = inputStream.read(data)) != -1) {
            //将读取到的本地文件流读取到HttpsURLConnection,进行上传
            out.write(data, 0, len);
            sum = len + sum;
        }

        System.out.println("上传图片大小为:" + sum);

        out.flush();
        inputStream.close();
        out.close();

        int code = con.getResponseCode(); //获取post请求返回状态
        System.out.println("code=" + code + " url=" + url);
        if (code == 200) {
            InputStream inputStream2 = con.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream2.read(data)) != -1) {
                bos.write(data, 0, len);
            }
            inputStream2.close();
            String content = bos.toString();
            bos.close();
            System.out.println("result =" + content);
            //将返回的json格式的字符串转化为json对象
            JSONObject json = JSONObject.parseObject(content);
            try {
                System.out.println("name=" + json.getString("name") + ", people=" + json.getString("people") + ", sex=" + json.getString("sex")
                        + ", id_number=" + json.getString("id_number") + ", type=" + json.getString("type") + ", address=" + json.getString("address")
                        + ", birthday=" + json.getString("birthday"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //断开HttpsURLConnection连接
        con.disconnect();
    }
}