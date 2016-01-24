package com.verybadalloc.designlib.network;

/**
 * Created by aambri on 15-06-06.
 */
public interface NetworkCallback {
    void onSuccess(String response);
    void onFailure(String reason);
}