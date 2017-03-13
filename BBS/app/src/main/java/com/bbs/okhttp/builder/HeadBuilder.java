package com.bbs.okhttp.builder;

import com.bbs.okhttp.OkHttpUtils;
import com.bbs.okhttp.request.OtherRequest;
import com.bbs.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
