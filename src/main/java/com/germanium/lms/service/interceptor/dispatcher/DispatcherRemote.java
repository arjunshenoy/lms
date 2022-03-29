package com.germanium.lms.service.interceptor.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.germanium.lms.service.interceptor.Interceptor;
import com.germanium.lms.service.interceptor.context.Context;

/**
 * An interface that specifies the methods that can be invoked remotely by the
 * client.
 */
public interface DispatcherRemote extends Remote {
    void register(Interceptor interceptor) throws RemoteException;
    void remove(Interceptor interceptor) throws RemoteException;
	void dispatch(Context context);
}
