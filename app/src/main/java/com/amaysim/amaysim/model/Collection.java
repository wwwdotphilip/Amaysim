package com.amaysim.amaysim.model;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class Collection implements Serializable {
    private Activity activity;
    private String json;
    private static final String TAG = "collection";
    private Data mData;
    private Data[] mIncluded;

    public Collection(Activity activity){
        this.activity = activity;
        json = loadJSONFromAsset();
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = activity.getAssets().open("collection.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public boolean validateUserId(String userId){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.getString("data");
            JSONObject account = new JSONObject(data);
            if (account.getString("type").equals(Data.TYPE.ACCOUNT)){
                if (mData == null){
                    mData = new Data(data);
                }
                if (mData.id.equals(userId)){
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Data getAccount(){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.getString("data");
            JSONObject account = new JSONObject(data);
            if (account.getString("type").equals(Data.TYPE.ACCOUNT)){
                if (mData == null){
                    mData = new Data(data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mData;
    }

    public Data[] getIncluded(){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("included");
            mIncluded = new Data[jsonArray.length()];
            for (int i=0; i < jsonArray.length(); i++) {
                String data = jsonArray.getString(i);
                mIncluded[i] = new Data(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mIncluded;
    }
}
