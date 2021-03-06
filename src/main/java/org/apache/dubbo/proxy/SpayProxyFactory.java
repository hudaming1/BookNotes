package org.apache.dubbo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.proxy.AbstractProxyInvoker;

public class SpayProxyFactory implements ProxyFactory {

	@Override
	public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
		return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable {
            	System.out.println("SpayProxyFactory.invoke " + methodName);
                return type.getMethod(methodName, parameterTypes).invoke(proxy, arguments);
            }
        };
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Invoker<T> invoker) throws RpcException {
		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { invoker.getInterface() }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		        return invoker.invoke(new RpcInvocation(method, invoker.getInterface().getName(), args)).recreate();
			}
		});
	}

	@Override
	public <T> T getProxy(Invoker<T> invoker, boolean generic) throws RpcException {
		throw new UnsupportedOperationException("Unsupported export generic service. url: " + invoker.getUrl());
	}
}
