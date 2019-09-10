package com.example.hiragarachangetest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GooLabPresenter implements IHiragaraChangeModel.getHiragaraAsynListener{
    private IHiragaraChangeView mView;
    private IHiragaraChangeModel mModel;

    public GooLabPresenter(IHiragaraChangeView view) {
        this.mView = view;
        mModel = new GooLabModel();
    }

    public void getChangeResult(String sentence) {
        mModel.getHiragaraAsyn(sentence);
    }

    @Override
    public void onSucessed(String rsp) {
        mView.onChangeSucceeded(getHiragaraFromResponse(rsp));
    }

    @Override
    public void onFaiulre() {
        mView.onChangeFailure();
    }

    private String getHiragaraFromResponse(String rsp) {
        return null;
    }
}
