package com.verybadalloc.designlib.events;

import com.squareup.otto.Bus;

/**
 * Created by aambri on 15-06-10.
 */
public class BusProvider {

    private static final Bus mInstance = new Bus();

    private BusProvider() {}

    public static Bus getInstance() {
        return mInstance;
    }
}
