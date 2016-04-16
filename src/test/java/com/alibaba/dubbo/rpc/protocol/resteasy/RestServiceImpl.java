package com.alibaba.dubbo.rpc.protocol.resteasy;

/**
 * @author sdcuike
 *
 *         Create At 2016年4月15日 下午6:01:32
 */
public class RestServiceImpl implements RestService {

    public ModelResult<String> test(RequestDto requestDto) {
        ModelResult<String> modelResult = new ModelResult<String>();
        modelResult.setData("test " + requestDto);
        return modelResult;
    }

}
