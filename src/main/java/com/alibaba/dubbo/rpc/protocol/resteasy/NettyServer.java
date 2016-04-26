/**
 * Copyright 1999-2014 dangdang.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.rpc.protocol.resteasy;

import java.util.Arrays;

import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.logging.Logger.LoggerType;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.rpc.protocol.resteasy.support.RPCContextHandlerIn;
import com.alibaba.dubbo.rpc.protocol.resteasy.support.RPCContextHandlerOut;

/**
 * Netty server can't support @Context injection of servlet objects since it's not a servlet container
 *
 * @author lishen
 */
public class NettyServer extends BaseRestServer {

    private final NettyJaxrsServer server = new NettyJaxrsServer();

    static {
        Logger.setLoggerType(LoggerType.SLF4J);
    }

    protected void doStart(URL url) {
        server.setPort(url.getPort());
        server.setExecutorThreadCount(url.getParameter(Constants.THREADS_KEY, Constants.DEFAULT_THREADS));
        server.setIoWorkerCount(url.getParameter(Constants.IO_THREADS_KEY, Constants.DEFAULT_IO_THREADS));
        // @author sdcuike Create At 2016年3月25日 下午2:08:20 https://github.com/sdcuike/dubbo-rpc-rest bug fix 网络传输包大小限制
        if (url.getParameter(Constants.PAYLOAD_KEY) != null) {
            server.setMaxRequestSize(Integer.parseInt(url.getParameter(Constants.PAYLOAD_KEY)));
        } else {
            server.setMaxRequestSize(Constants.DEFAULT_PAYLOAD);
        }
        // @author sdcuike Create At 2016年3月25日 下午2:08:20 https://github.com/sdcuike/dubbo-rpc-rest bug fix 域名设置
        // String host = (StringUtils.isBlank(url.getHost()) || NetUtils.isInvalidLocalHost(url.getHost())) ? NetUtils.ANYHOST : url.getHost();
        // server.setHostname(host);
        if (!url.isAnyHost() && NetUtils.isValidLocalHost(url.getHost())) {
            server.setHostname(url.getHost());
        }

        // 获取ip地址和头部信息
        server.setHttpChannelHandlers(Arrays.asList(new RPCContextHandlerIn(), new RPCContextHandlerOut()));
        server.start();
    }

    public void stop() {
        server.stop();
    }

    protected ResteasyDeployment getDeployment() {
        return server.getDeployment();
    }
}
