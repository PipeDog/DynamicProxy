package com.pipedog.dynamicproxy.demo.dyc;

import com.pipedog.dycproxy.DynamicProxy;

import java.lang.reflect.Method;

public class ConsoleLogger implements Logger {

    public ConsoleLogger() {
    }

    @Override
    public void log(String message) {
        System.out.println(message);
    }

    public static class ProcessorImpl implements DynamicProxy.Processor {
        @Override
        public void beforeInvokeMethod(Object proxy, Method method, Object[] args) {
            System.out.println(">>>>>>> aaaaaaa");
        }

        @Override
        public void afterInvokeMethod(Object proxy, Method method, Object[] args) {
            System.out.println(">>>>>>> bbbbbbb");
        }
    }

}
