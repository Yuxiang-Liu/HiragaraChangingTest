package com.example.hiragarachangetest;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GooLabModel implements IHiragaraChangeModel{
    private final String TAG = "GOOLABMODEL";

    private final String GOO_LAB_URL = "https://labs.goo.ne.jp/api/hiragana";

    private final String GOO_LAB_NAME_APP_ID = "app_id";
    private final String GOO_LAB_NAME_SENTENCE = "sentence";
    private final String GOO_LAB_NAME_OUTPUT_TYPE = "output_type";
    private final String GOO_LAB_VAL_API_KEY = "bb729208b952aa1aacda95b202ecd5571ee6fc7116805e0f223e7842bf5a4e95";
    private final String GOO_LAB_VAL_OUTPUT_TYPE = "hiragana";
    private final String GOO_LAB_RSP_CONVERTED = "converted";


    @Override
    public void getHiragaraAsyn(String sentence, IGetHiragaraListener listener) {
        Log.i(TAG, "getHiragaraAsyn, sentence is " + sentence);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add(GOO_LAB_NAME_APP_ID, GOO_LAB_VAL_API_KEY)
                .add(GOO_LAB_NAME_SENTENCE, sentence)
                .add(GOO_LAB_NAME_OUTPUT_TYPE, GOO_LAB_VAL_OUTPUT_TYPE)
                .build();
        Request req = new Request.Builder().url(GOO_LAB_URL).post(body).build();

        Log.i(TAG, "Request is " + req.toString());

        Call call = okHttpClient.newCall(req);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                listener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, response.protocol() + " " +response.code() + " " + response.message());
                String rspString = response.body().string();
                Log.d(TAG, "response is" + rspString);

                String result = getResultFromRsp(rspString);
                if (result != null) {
                    listener.onSucceeded(result);
                } else {
                    Log.e(TAG, "parse response error!");
                    listener.onFailure();
                }
            }
        });
    }

    private String getResultFromRsp(String rspString) {
        Log.i(TAG, "getResultFromRsp rspString is" + rspString);
        try{
            JSONObject obj = new JSONObject(rspString);
            String name = obj.getString(GOO_LAB_RSP_CONVERTED);
            return name;
        }catch (JSONException e){
            Log.e(TAG, "getResultFromRsp error!");
            return null;

        }
    }

}
