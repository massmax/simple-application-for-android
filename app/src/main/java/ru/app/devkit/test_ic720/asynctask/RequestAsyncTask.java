package ru.app.devkit.test_ic720.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ru.app.devkit.test_ic720.R;
import ru.app.devkit.test_ic720.requester.IRequester;
import ru.app.devkit.test_ic720.requester.impl.JSONImpl;

public class RequestAsyncTask extends AsyncTask<String, String, String> {
    public static String TAG = "RequestAsyncTask";
    private static final String URL_STRING = "http://ip.pycox.com/json/";
    private AsyncInterface asIn = null;

    Context context;

    public RequestAsyncTask(Context c){
        context = c;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = URL_STRING + params[0];
        StringBuilder result = new StringBuilder();
        try {
            IRequester parser = new JSONImpl();
            JSONObject jsonObject = (JSONObject) parser.getDataFromUrl(url);

            if (jsonObject == null) {
                return null;
            }

            if (jsonObject.getString("country_name") != null && jsonObject.getString("country_code3") != null ) {

                String city = EmptyOrString(jsonObject.getString("city"));
                String region_name = EmptyOrString(jsonObject.getString("region_name"));
                String time_zone = EmptyOrString(jsonObject.getString("time_zone"));
                String longitude = EmptyOrString(jsonObject.getString("longitude"));
                String country_code = EmptyOrString(jsonObject.getString("country_code3"));
                String latitude = EmptyOrString(jsonObject.getString("latitude"));
                String country_name = EmptyOrString(jsonObject.getString("country_name"));

                result = result.append(context.getResources().getString(R.string.city)).append(city).append("\n")
                        .append(context.getResources().getString(R.string.region_name)).append(region_name).append("\n")
                        .append(context.getResources().getString(R.string.time_zone)).append(time_zone).append("\n")
                        .append(context.getResources().getString(R.string.longitude)).append(longitude).append("\n")
                        .append(context.getResources().getString(R.string.latitude)).append(latitude).append("\n")
                        .append(context.getResources().getString(R.string.country_name)).append(country_name).append("\n")
                        .append(context.getResources().getString(R.string.country_code)).append(country_code);

            } else if (jsonObject.getString("error") != null) {
                String error = EmptyOrString(jsonObject.getString("error"));
                result = result.append(context.getResources().getString(R.string.error)).append(error).append("\n");
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show();
            asIn.receiveData(result);
        } else {
            Toast.makeText(context, R.string.no_data, Toast.LENGTH_SHORT).show();
        }
    }

    private String EmptyOrString(String str) {
        return str.equals("null") ? "" : str;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public interface AsyncInterface {
        public void receiveData(String result);
    }

    public void receiveDataListener (AsyncInterface asyncInterface) {
        this.asIn = asyncInterface;
    }
}

