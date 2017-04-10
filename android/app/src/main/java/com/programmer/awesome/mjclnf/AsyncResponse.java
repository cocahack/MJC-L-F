package com.programmer.awesome.mjclnf;

/**
 * Created by USER on 2016-11-01.
 */

public interface AsyncResponse {
    void processFinish(Object output);
    void processFinish(String output);
}
