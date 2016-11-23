package com.xiongl.weixin.service;

import com.xiongl.weixin.sdk.Constant;
import com.xiongl.weixin.sdk.TagApi;
import com.xiongl.weixin.sdk.TagClientList;
import com.xiongl.weixin.sdk.UserInfo;
import retrofit2.Call;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016-11-23.
 */
public class TagService extends BaseService {
    private static TagApi tagApi = RetrofitService.getQyApiInstance(true).create(TagApi.class);

    public List<UserInfo> getUserInfoList(String tagId) {
        String accessToken = AccessTokenService.getInstance().getAccessToken(Constant.CONSULT_APP);
        Call<TagClientList> resp = tagApi.getTagClientList(accessToken, tagId);

        TagClientList tcl = getResult(resp, "getUserInfoList");
        if (null == tcl) {
            return Collections.EMPTY_LIST;
        }

        return tcl.getUserInfoList();
    }
}
