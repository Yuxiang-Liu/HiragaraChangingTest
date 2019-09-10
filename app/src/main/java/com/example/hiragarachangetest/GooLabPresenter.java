package com.example.hiragarachangetest;

import android.util.Log;

public class GooLabPresenter implements IHiragaraChangeModel.IGetHiragaraListener {
    private static final String TAG = "GooLabPresenter";
    private final String OUTPUT_KEY = "converted";
    private IHiragaraChangeView mView;
    private IHiragaraChangeModel mModel;


    public GooLabPresenter(IHiragaraChangeView view) {
        this.mView = view;
        mModel = new GooLabModel();
        Log.i(TAG, "Constructed");
    }

    public void getChangeResult(String sentence) {
        mModel.getHiragaraAsyn(sentence, this);
        Log.i(TAG, "Call getHiragaraAsyn, sentence is " + sentence);
    }

    @Override
    public void onSucceeded(String result) {
        mView.onChangeSucceeded(result);
        Log.i(TAG, "onSucceeded, response is " + result);
    }

    @Override
    public void onFailure() {
        mView.onChangeFailure();
        Log.i(TAG, "onFailure");
    }
}
