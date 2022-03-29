package com.germanium.lms.service.interceptor;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.germanium.lms.service.interceptor.context.ContextRemote;

/**
 * An interface that defines the signatures of a callback method that the server
 * invokes automatically via the dispatching mechanism when the corresponding
 * events occur.
 */
public interface Interceptor extends Remote {
    void callback(ContextRemote context) throws RemoteException;
}

