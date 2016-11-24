package com.heyuo.qy.controller;

import com.heyuo.qy.bo.api.UserInfoWithLevel;
import com.heyuo.qy.bo.api.UserInfoWithLevelJson;
import com.heyuo.qy.model.QyLevel;
import com.heyuo.qy.service.QyLevelService;
import com.jfinal.core.Controller;
import com.xiongl.weixin.sdk.UserInfo;
import com.xiongl.weixin.service.TagService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-23.
 */
public class QyOtherApiController extends Controller {

    private TagService tagService = new TagService();

    private QyLevelService qyLevelService = new QyLevelService();

    public void clientLevelChange() {
        QyLevel qyLevel = new QyLevel();
        qyLevel.setWxUser(getPara("userId"));
        qyLevel.setLevel(getParaToInt("level"));
        qyLevelService.saveOrUpdate(qyLevel);
        renderJson("{\"error\":\"0\"}");
    }

    public void clientList() {
        // client标志为 1
        List<UserInfoWithLevel> uilList = new LinkedList<UserInfoWithLevel>();

        UserInfoWithLevelJson json = new UserInfoWithLevelJson();
        json.setUserInfo(uilList);

        List<UserInfo> uiList = tagService.getUserInfoList("1");
        for (UserInfo ui : uiList) {
            UserInfoWithLevel uil = new UserInfoWithLevel();
            uil.setUserId(ui.getUserId());
            uil.setName(ui.getName());

            Integer level = qyLevelService.getLeve(uil.getUserId());
            uil.setLevel(level);
            if (level == 1) {
                uil.setLevelTip("700元，档次1");
            } else if (level == 2) {
                uil.setLevelTip("3800元，档次2");
            } else {
                uil.setLevelTip("未设置咨询档次");
            }


            uilList.add(uil);
        }

        renderJson(json);
    }
}
