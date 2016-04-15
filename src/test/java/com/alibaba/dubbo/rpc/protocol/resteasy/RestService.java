package com.alibaba.dubbo.rpc.protocol.resteasy;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author sdcuike
 *
 *         Create At 2016年4月15日 下午5:56:03
 */
@Path("restService")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface RestService {

    @Path("/test")
    @POST
    ModelResult<String> test(RequestDto requestDto);
}
