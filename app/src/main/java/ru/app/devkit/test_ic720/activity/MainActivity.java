package ru.app.devkit.test_ic720.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.app.devkit.test_ic720.R;
import ru.app.devkit.test_ic720.asynctask.RequestAsyncTask;

import static ru.app.devkit.test_ic720.util.UtilMethods.validityCheck;


public class MainActivity extends Activity implements View.OnClickListener {

    public static String TAG = "MainActivity";

    public Button mBtnRequest;
    public EditText mEditTextRequestData;
    public TextView mTextViewResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnRequest = (Button) findViewById(R.id.btnSend);
        mEditTextRequestData = (EditText) findViewById(R.id.editRequest);
        mEditTextRequestData.setMaxLines(1);
        mTextViewResponseData = (TextView) findViewById(R.id.textResponse);
        mBtnRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (validityCheck(mEditTextRequestData.getText().toString())) {
            Log.d(TAG, "Запрос в AsyncTask");
            Toast.makeText(this, R.string.send_hint, Toast.LENGTH_SHORT).show();
            mTextViewResponseData.setText("");
            RequestAsyncTask task = new RequestAsyncTask(this);
            task.receiveDataListener( new RequestAsyncTask.AsyncInterface() {
                @Override
                public void receiveData(String result) {
                    mTextViewResponseData.setText(result);
                }
            });
            task.execute(mEditTextRequestData.getText().toString());
        } else {
            Toast.makeText(this, R.string.error_field, Toast.LENGTH_SHORT).show();
        }
    }
}
