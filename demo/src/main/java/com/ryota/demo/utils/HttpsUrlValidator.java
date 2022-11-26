package com.ryota.demo.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description TODO
 * @Date 2022/11/26 18:32
 * @Author ryota
 */
public class HttpsUrlValidator {
    public static void main(String[] args) {
    }

    static HostnameVerifier hv = new HostnameVerifier() {
        @Override
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                    + session.getPeerHost());
            return true;
        }
    };

    public final static String retrieveResponseFromServer(final String url) {
        HttpURLConnection connection = null;

        try {
            URL validationUrl = new URL(url);
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

            connection = (HttpURLConnection) validationUrl.openConnection();
            final BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line;
            final StringBuffer stringBuffer = new StringBuffer(255);

            synchronized (stringBuffer) {
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line);
                    stringBuffer.append("\n");
                }
                return stringBuffer.toString();
            }

        } catch (final IOException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (final Exception e1) {
            System.out.println(e1.getMessage());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static String sendPostUplodFile(String filePath,String fileName) {
        DataOutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
//            URL realUrl = new URL( "http://10.72.66.27:9090/oss/upload/csp/uploadMaterial");
            URL realUrl = new URL( "https://csp.cet.cnooc.com.cn/oss/upload/csp/uploadMaterial");
            HttpsUrlValidator.trustAllHttpsCertificates();
            //打开和URL之间的连接
            sun.net.www.protocol.http.HttpURLConnection conn = (sun.net.www.protocol.http.HttpURLConnection)realUrl.openConnection();
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
            File file = FileUtils.urlToFile(filePath,fileName);

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
    public static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

}
