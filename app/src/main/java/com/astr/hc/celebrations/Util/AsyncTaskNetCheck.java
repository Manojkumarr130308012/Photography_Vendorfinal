package com.astr.hc.celebrations.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTaskNetCheck extends AsyncTask<String, String, Boolean> {
    ProgressDialog _progressDialog;
    Context context;
    INetworkCallBack iNetworkCallBack;
    boolean isTryAgain = false;

    public AsyncTaskNetCheck(Context context, INetworkCallBack inetworkCallBack, boolean istryagain) {
        this.context = context;
        this.iNetworkCallBack = inetworkCallBack;
        this.isTryAgain = istryagain;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressBar();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return AppUtil.isNetworkConnected(context) && AppUtil.isInternetAvailable();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(isTryAgain){
            iNetworkCallBack.NetWorkCallBackResultTryAgain(result);
        }else{
            iNetworkCallBack.NetWorkCallBackResultDismiss(result);
        }

        hideProgressBar();
    }

    void showProgressBar() {
        _progressDialog = new ProgressDialog(context);
        _progressDialog.setMessage("Checking internet..");
        _progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _progressDialog.setCancelable(false);
        _progressDialog.show();
    }

    /**
     * Removes the progress bar
     */
    void hideProgressBar() {
        if (_progressDialog != null) {
            _progressDialog.dismiss();
        }
    }
}