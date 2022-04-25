package com.germanium.lms.service.interceptor.aspects;
import com.germanium.lms.service.interceptor.InterceptibleFramework;
import com.germanium.lms.service.interceptor.context.Context;

public aspect InterceptorAspect {

    // cut the interceptible methods
    pointcut interceptibleEvent(): call(@Interceptible * *(..));

     // create context object and invoke dispatcher
    before() : interceptibleEvent() {
        //System.out.println("An interceptible event triggered.");
        InterceptibleFramework interceptibleFramework
                = (InterceptibleFramework) thisJoinPoint.getTarget();
        String event = interceptibleFramework.getEvents().get(
                thisJoinPointStaticPart.getSignature().toLongString());
        Context context = interceptibleFramework.createContext(event);
        interceptibleFramework.invokeDispatcher(event, context);
    }
}