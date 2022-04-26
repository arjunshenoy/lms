package com.germanium.lms.service.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.germanium.lms.service.interceptor.context.*;
import com.germanium.lms.service.interceptor.annotation.Accessible;
import com.germanium.lms.service.interceptor.annotation.Interceptible;
import com.germanium.lms.service.interceptor.annotation.Mutable;
import com.germanium.lms.service.interceptor.dispatcher.*;
/**
 * This class represents a concrete framework in the interceptor design pattern.
 * It can be subclassed and in a subclass the methods that the application can
 * intercept are annotated with @interceptible. Also, the attributes that
 * the application can read/write are annotated with @Accessible/@Mutable.
 *
 */
public class InterceptibleFramework {

	Logger logger = LoggerFactory.getLogger(InterceptibleFramework.class);
    private HashMap<String, Dispatcher> dispatchers = new HashMap<>();

    // mapping from method signatures to event names
    private HashMap<String, String> events = new HashMap<>();

    /**
     * Constructor
     *
     * For each method annotated with @Interceptible, create a dispatcher.
     */
    public InterceptibleFramework() {
        for(Method method : this.getClass().getDeclaredMethods()) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for(Annotation annotation : annotations){
                if(annotation instanceof Interceptible) {
                    Interceptible interceptible = (Interceptible) annotation;
                    // associate a method signature with an event
                    events.put(method.toString(), interceptible.event());
					Dispatcher dispatcher = new Dispatcher();
					// associate an event with a dispatcher
					dispatchers.put(interceptible.event(), dispatcher);
                }
            }
        }
    } // end constructor

    /**
     * Accessor
     *
     */
    public HashMap<String, String> getEvents() {
        return events;
    }
    
    protected void setInterceptor(Interceptor i, String event) {
    	dispatchers.get(event).register(i);
    }
    
    protected void rmInterceptor(Interceptor i, String event) {
    	dispatchers.get(event).remove(i);;
    }

    /**
     * Create a context.
     *
     */
    public Context createContext(String event) {
        //System.out.println("Creating a context on event " + event);
        Context context = new Context(this);
        setAccessibles(context, event);
        setMutables(context, event);
        return context;
    }
    
    void setAccessibles(Context context, String event) {
    	// look for fields that are associated with the event
        for(Field field : this.getClass().getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof Accessible) {
                    Accessible accessible = (Accessible) annotation;
                    if(Arrays.asList(accessible.event()).contains(event)) {
                        field.setAccessible(true);
                        context.putAccessible(field.getName(), field);
                    }
                }
            }
        } // end outer for
    }
    
    public void setMutables(Context context, String event) {
    	// look for fields that are associated with the event
        for(Field field : this.getClass().getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof Mutable) {
                    Mutable accessible = (Mutable) annotation;
                    if(Arrays.asList(accessible.event()).contains(event)) {
                        field.setAccessible(true);
                        context.putMutable(field.getName(), field);
                    }
                }
            }
        } // end outer for
    }

    /**
     * Invoke a dispatcher.
     *
     *     */
    public void invokeDispatcher(String event, Context context) {
        //System.out.println("Invoking dispatcher " + event);
        dispatchers.get(event).dispatch(context);
    }
}