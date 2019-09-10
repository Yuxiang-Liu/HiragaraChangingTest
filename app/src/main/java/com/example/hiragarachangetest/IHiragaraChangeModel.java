package com.example.hiragarachangetest;

import okhttp3.Call;

public interface IHiragaraChangeModel {
    void getHiragaraAsyn(String sentence);
    interface getHiragaraAsynListener {
        void onSucessed(String result);
        void onFaiulre();
    }
}
