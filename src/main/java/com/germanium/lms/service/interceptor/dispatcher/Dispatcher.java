package com.germanium.lms.service.interceptor.dispatcher;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.germanium.lms.service.interceptor.context.*;

import com.germanium.lms.service.interceptor.Interceptor;

/**
 * A class that configures and triggers concrete interceptors.
 *
 */
public class Dispatcher {

    private List<Interceptor> interceptors = new ArrayList<>();

	Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    public Dispatcher() {}

    /**
     * Subscribe a concrete interceptor with the server.
     *
     */
    public void register(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * Un-subscribe a concrete interceptor with the server.
     *
     */
    public void remove(Interceptor interceptor) {
        interceptors.remove(interceptor);
    }

    /**
     * Invoke the callback methods of the interceptors.
     */
    public void dispatch(Context context) {
		for (Interceptor interceptor : interceptors) {
			interceptor.callback(context);
		}
    }
}