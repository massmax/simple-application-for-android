package ru.app.devkit.test_ic720.requester.impl;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import ru.app.devkit.test_ic720.requester.IRequester;

public class JSONImpl implements IRequester {

    public static String TAG = "JSONImpl";
    private JSONObject jsonObject;

    public Object getDataFromUrl(String url) {
        InputStream inputStream = connectUrl(url);
        String resultJson = readAndConversionData(inputStream);

        try {
            jsonObject = new JSONObject(resultJson);
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка разбора данных " + e.toString());
        }

        return jsonObject;
    }

    private InputStream connectUrl(String url) {
        InputStream stream = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet requestGet = new HttpGet(url);
            HttpResponse httpResponse = client.execute(requestGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            stream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Кодировка не поддерживается " + e.toString());
        } catch (ClientProtocolException e) {
            Log.e(TAG, "Ошибка в протоколе HTTP " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "IOException " + e.toString());
        }
        return stream;
    }

    private String readAndConversionData(InputStream is) {
        String result = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("n");
            }
            is.close();
            result = stringBuilder.toString();

        } catch (Exception e) {
            Log.e(TAG, "Ошибка преобразования " + e.toString());
        }
        return result;
    }
}

