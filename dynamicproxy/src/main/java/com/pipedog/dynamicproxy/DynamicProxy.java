package com.pipedog.dycproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liang
 * @time 2022/06/29
 * @desc 动态代理简单包装
 */
public class DynamicProxy {

    public interface Processor {
        void beforeInvokeMethod(Object proxy, Method method, Object[] args);
        void afterInvokeMethod(Object proxy, Method method, Object[] args);
    }

    public static class Builder {

        private Object target;
        private List<Processor> processors = new LinkedList<>();

        public Builder() {
        }

        public Builder target(Object target) {
            this.target = target;
            return this;
        }

        public Builder addProcessor(Processor processor) {
            this.processors.add(processor);
            return this;
        }

        public DynamicProxy build() {
            return new DynamicProxy(target, processors);
        }

    }

    private Object target;
    private List<Processor> processors;
    private Object dycProxy;

    private DynamicProxy(Object target, List<Processor> processors) {
        if (target == null) {
            throw new RuntimeException("Class `DynamicProxy` constructor argument `target` can not be nil!");
        }

        this.target = target;
        this.processors = new LinkedList<>(processors);
    }

    public <T> T getProxy() {
        if (dycProxy == null) {
            createTargetProxy();
        }
        return (T) dycProxy;
    }


    // PRIVATE METHODS

    private void createTargetProxy() {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();

        dycProxy = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                beforeInvokeMethod(proxy, method, args);
                Object result = method.invoke(target, args);
                afterInvokeMethod(proxy, method, args);
                return result;
            }
        });
    }

    private void beforeInvokeMethod(Object proxy, Method method, Object[] args) {
        if (processors.isEmpty()) {
            return;
        }

        for (Processor p : processors) {
            p.beforeInvokeMethod(proxy, method, args);
        }
    }

    private void afterInvokeMethod(Object proxy, Method method, Object[] args) {
        if (processors.isEmpty()) {
            return;
        }

        for (Processor p : processors) {
            p.afterInvokeMethod(proxy, method, args);
        }
    }

}