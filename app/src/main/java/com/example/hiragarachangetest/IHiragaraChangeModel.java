package com.example.hiragarachangetest;

import okhttp3.Call;

public interface IHiragaraChangeModel {
    void getHiragaraAsyn(String sentence, IGetHiragaraListener listener);
    interface IGetHiragaraListener {
        void onSucceeded(String result);
        void onFailure();
    }
}
