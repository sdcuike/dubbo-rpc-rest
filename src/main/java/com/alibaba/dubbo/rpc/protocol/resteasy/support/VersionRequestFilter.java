package com.alibaba.dubbo.rpc.protocol.resteasy.support;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import io.netty.util.internal.StringUtil;

/**
 * @author sdcuike
 *
 *         Create At 2016年4月13日 下午3:53:38
 */

@PreMatching
@Priority(Priorities.AUTHENTICATION - 100)
public class VersionRequestFilter implements ContainerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String HEADER_VERSION = "version";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String version = requestContext.getHeaderString(HEADER_VERSION);
        if (StringUtil.isNullOrEmpty(version)) {
            return;
        }

        URI requestUri = requestContext.getUriInfo().getRequestUri();
        try {
            URI newUri = new URI(requestUri.getScheme(), requestUri.getAuthority(), "/" + version + requestUri.getPath(), requestUri.getQuery(), requestUri.getFragment());
            requestContext.setRequestUri(newUri);

        } catch (URISyntaxException e) {
            String info = String.format("requestContext.setRequestUri error: version[%s]=>requestUri[%s]", version, requestUri);
            log.error(info, e);
        }

    }

}
