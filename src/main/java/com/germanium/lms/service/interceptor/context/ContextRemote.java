package com.germanium.lms.service.interceptor.context;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface that specifies the methods that can be invoked remotely by a
 * concrete interceptor.
 * 
 * We are creating a generic context object which can be used to get and set the values
 * of any fields with @mutable and @accessible annotation 
 * within methods defined with @interceptable annotation
 *  
 *  */
public interface ContextRemote extends Remote {
    Object getValue(String key) throws RemoteException;
    void setValue(String key, Object value) throws RemoteException;
}
