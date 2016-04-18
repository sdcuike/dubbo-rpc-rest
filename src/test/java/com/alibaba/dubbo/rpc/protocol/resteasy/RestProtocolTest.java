package com.alibaba.dubbo.rpc.protocol.resteasy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jboss.resteasy.core.ResourceMethodRegistry;
import org.jboss.resteasy.plugins.server.embedded.SecurityDomain;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.alibaba.dubbo.rpc.ServiceClassHolder;

/**
 * 
 * @author sdcuike
 *
 *         Create At 2016年4月15日 下午5:55:15
 */
public class RestProtocolTest {

    @Test
    public void testRestProtocol() throws InterruptedException {
        RestService server = new RestServiceImpl();
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        URL url = URL.valueOf("resteasy://127.0.0.1:5342/" + RestService.class.getName() + "?version=1.0.0&logger=slf4j");
        ServiceClassHolder.getInstance().pushServiceClass(RestService.class);
        Exporter<RestService> exporter = protocol.export(proxyFactory.getInvoker(server, RestService.class, url));
        Invoker<RestService> invoker = protocol.refer(RestService.class, url);
        RestService client = proxyFactory.getProxy(invoker);
        RequestDto requestDto = new RequestDto();
        requestDto.setAge(88);
        requestDto.setName("doctor who");
        ModelResult<String> modelResult = client.test(requestDto);

        String expected = "test " + ToStringBuilder.reflectionToString(requestDto, ToStringStyle.SHORT_PREFIX_STYLE);
        System.out.println(expected);
        Assert.assertEquals(expected, modelResult.getData());
        invoker.destroy();

        TimeUnit.MINUTES.sleep(1);
        exporter.unexport();
    }

    /**
     * // lsof -i:8990
     * // COMMAND PID USER FD TYPE DEVICE SIZE/OFF NODE NAME
     * 
     * @throws InterruptedException
     */
    @Test
    public void test_host_for_LISTEN_ADDRESS() throws InterruptedException {

        // String hostname = "10.100.0.25";
        // String hostname = "127.0.0.1";//java 29782 username 146u IPv6 0x41980098a5e8db51 0t0 TCP localhost:8990 (LISTEN)
        // String hostname = "localhost";

        String hostname = "0.0.0.0";// java 29827 username 146u IPv6 0x41980098a5e8db51 0t0 TCP *:8990 (LISTEN)
        int port = 8990;
        SecurityDomain securityDomain = null;
        String rootResourcePath = "/";
        int ioWorkerCount = Runtime.getRuntime().availableProcessors() * 2;
        int executorThreadCount = 12;

        NettyJaxrsServer nettyJaxrsServer = new NettyJaxrsServer();

        nettyJaxrsServer.setHostname(hostname);
        nettyJaxrsServer.setPort(port);
        nettyJaxrsServer.setSecurityDomain(securityDomain);
        nettyJaxrsServer.setRootResourcePath(rootResourcePath);

        nettyJaxrsServer.setIoWorkerCount(ioWorkerCount);
        nettyJaxrsServer.setExecutorThreadCount(executorThreadCount);

        ResteasyDeployment deployment = nettyJaxrsServer.getDeployment();
        List<Object> resources = new ArrayList<Object>();
        deployment.setResources(resources);
        nettyJaxrsServer.start();
        ResourceMethodRegistry resourceMethodRegistry = (ResourceMethodRegistry) deployment.getRegistry();
        resourceMethodRegistry.setWiderMatching(true);// 不设置也可以

        deployment.getRegistry().addSingletonResource(new RestServiceImpl());
        TimeUnit.MINUTES.sleep(1);

    }

}
