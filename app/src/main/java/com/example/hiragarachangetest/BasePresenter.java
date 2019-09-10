package com.example.hiragarachangetest;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V, M>{
    private WeakReference<V> mWeakView;
    protected M model;

    public V getView() {return null;}
    public void attachView(V view) {
        this.mWeakView = new WeakReference<V>(view);
    }

    public void dettachView() {
        if (this.mWeakView != null) {
            this.mWeakView.clear();
            this.mWeakView = null;
        }
    }

    public boolean isAttachView() {
        return this.mWeakView != null && this.mWeakView.get() != null;
    }
}
