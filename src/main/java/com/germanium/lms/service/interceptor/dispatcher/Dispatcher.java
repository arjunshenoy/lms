package com.germanium.lms.service.interceptor.dispatcher;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
public class Dispatcher extends UnicastRemoteObject implements DispatcherRemote {

    private List<Interceptor> interceptors = new ArrayList<>();

	Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    public Dispatcher() throws RemoteException {}

    /**
     * Subscribe a concrete interceptor with the server.
     *
     */
    public void register(Interceptor interceptor) throws RemoteException {
        interceptors.add(interceptor);
    }

    /**
     * Un-subscribe a concrete interceptor with the server.
     *
     */
    public void remove(Interceptor interceptor) throws RemoteException {
        interceptors.remove(interceptor);
    }

    /**
     * Invoke the callback methods of the interceptors.
     */
    public void dispatch(Context context) {
        try {
            for (Interceptor interceptor : interceptors) {
                interceptor.callback(context);
            }
        } catch(RemoteException e) {
        	logger.info("failed to intercept callbacks");
        }
    }
}
