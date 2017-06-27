package com.zfj.android.loadarticles.tools;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zfj_ on 2017/5/31.
 */

public class GetJSONObject {
    private static JSONObject mJob;

    public static <T> T getJSONObject(String result, Class<T> detail) {
        try {
            mJob = new JSONObject(result);
            if (mJob.getInt("status") == 1 && mJob.getString("msg").equals("成功")) {
                Gson gson = new Gson();
                String data = mJob.getString("data");
                T a = gson.fromJson(data, detail);
                return a;
            }else{
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
