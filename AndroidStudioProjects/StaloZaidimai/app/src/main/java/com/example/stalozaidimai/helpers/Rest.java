package com.example.stalozaidimai.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rest {
    private static BufferedWriter bufferedWriter;
    //private static OutputStream outputStream;

    public static String sendPost(String postUrl, String jsonInfo) throws IOException {

        URL url = new URL(postUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(20000);
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setRequestProperty("Content-type", "application/json; charset = UTF-8");
        httpURLConnection.setRequestProperty("Accept", "*/*");
        //httpURLConnection.setRequestProperty("Content-Length", String.valueOf(jsonInfo.length()));
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        //outputStream = httpURLConnection.getOutputStream();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(jsonInfo);

        bufferedWriter.close();
        outputStream.close();
        int code = httpURLConnection.getResponseCode();
        System.out.println("Response code was "+code);
        //lol

        if (code == httpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            return response.toString();
        } else {
            return "Error";
        }
    }
}
