package com.zfj.android.loadarticles.network;

import android.os.AsyncTask;

import com.zfj.android.loadarticles.tools.GetJSONObject;
import com.zfj.android.loadarticles.datas.Article;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zfj_ on 2017/6/11.
 */

public class LoadingAsyncHelper {

    public static final String COM_ZFJ_ANDROID_LOADARTICLES_DATAS_ARTICLE = "com.zfj.android.loadarticles.datas.Article";

    public static void requestAritcle(String url, onRequestListener listener) {
        new LoadingAsyncTask(url, listener).execute();
    }

    public interface onRequestListener {
        void onSuccess(Article article);

        void onFail(String msg);
    }

    public static class LoadingAsyncTask extends AsyncTask<String, Void, Article> {
        private String mUrl;
        private onRequestListener mListener;

        public LoadingAsyncTask(String mUrl, onRequestListener mListener) {
            this.mUrl = mUrl;
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Article article) {
            super.onPostExecute(article);
            if (article != null && mListener != null) {
                mListener.onSuccess(article);
            } else {
                if (mListener != null) {
                    mListener.onFail("article为null");
                }
            }
        }

        @Override
        protected Article doInBackground(String... params) {
            if (mUrl == null) {
                if (mListener != null) {
                    mListener.onFail("url为空！");
                    return null;
                }
            }
            Article article = null;
            try {
                URL url = new URL(mUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(10 * 1000);
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = con.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > -1) {
                        baos.write(buf, 0, len);
                    }
                    String result = baos.toString();
                    in.close();
                    baos.close();
                    article = (Article) GetJSONObject.getJSONObject(result,
                            Class.forName(COM_ZFJ_ANDROID_LOADARTICLES_DATAS_ARTICLE));
                    return article;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                if (mListener != null) {
                    mListener.onFail(e.getMessage());
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (mListener != null) {
                    mListener.onFail(e.getMessage());
                    return null;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                if (mListener != null) {
                    mListener.onFail(e.getMessage());
                    return null;
                }
            }
            return article;
        }
    }
}

