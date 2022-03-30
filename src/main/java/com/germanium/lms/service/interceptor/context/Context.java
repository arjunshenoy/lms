package com.germanium.lms.service.interceptor.context;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import com.germanium.lms.service.interceptor.InterceptibleFramework;

/**
 * This class defines accessor and mutator methods that allow a concrete
 * interceptor to access and modify a server's internal state.
 */
public class Context extends UnicastRemoteObject implements ContextRemote {

	// the server instance that created this context
	private InterceptibleFramework interceptibleFramework;

	private HashMap<String, Field> accessibles = new HashMap<>();
	private HashMap<String, Field> mutables = new HashMap<>();

	public Context(InterceptibleFramework interceptibleFramework) throws RemoteException {
		this.interceptibleFramework = interceptibleFramework;
	}

	/**
	 * Accessor
	 *
	 */
	public Object getValue(String key) throws RemoteException {
		Object o = null;
		try {
			o = accessibles.get(key).get(interceptibleFramework);
		} catch (IllegalAccessException e) {
		}
		return o;
	}

	/**
	 * Mutator
	 *
	 */
	public void setValue(String key, Object value) throws RemoteException {
		try {
			mutables.get(key).set(interceptibleFramework, value);
		} catch (IllegalAccessException e) {
		}
	}

	/**
	 * Add a field that a concrete interceptor can access.
	 *
	 */
	public void putAccessible(String key, Field value) {
		accessibles.put(key, value);
	}

	/**
	 * Add a field that a concrete interceptor can change.
	 */
	public void putMutable(String key, Field value) {
		mutables.put(key, value);
	}
}
