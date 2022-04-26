package com.germanium.lms.service.interceptor;

import com.germanium.lms.service.interceptor.context.Context;

public interface Interceptor {
    void callback(Context context);
}

