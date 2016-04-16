package com.alibaba.dubbo.rpc.protocol.resteasy;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
    public void testRestProtocol() {
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

        Assert.assertEquals("test" + ToStringBuilder.reflectionToString(requestDto, ToStringStyle.SHORT_PREFIX_STYLE), modelResult.getData());
        invoker.destroy();

        exporter.unexport();
    }

}
