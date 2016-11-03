package com.heyuo.qy;

import com.xiongl.weixin.sdk.ApiConfig;
import com.xiongl.weixin.sdk.Constant;
import com.xiongl.weixin.service.AccessTokenService;
import com.xiongl.weixin.service.DownloadService;

/**
 * Created by Administrator on 2016-11-2.
 */
public class Test {
    public static void main(String[] args) {
        ApiConfig ac = new ApiConfig();
        ac.setCorpId("wxc7fbca5e6ed8931f");
        ac.setCorpSecret("DscxQEO18iH0F5eUBrXaB54UsW4GMS-Nh-9a8Qlmiomm81uAbic0cgSgZC53-d7T");

        AccessTokenService.configMap.put(Constant.CONSULT_APP, ac);

        DownloadService ds = new DownloadService();
        //ds.download("voice", "18B6I65OqPgvtdJijhDSijie6-5c2KnfWnlKeNvGy8J5XSTGRsaW-n9Y7TePBX0AqkkCiTPeS2AhO-hGIug7wEw");
        ds.download("voice", "11", "18B6I65OqPgvtdJijhDSijie6-5c2KnfWnlKeNvGy8J5XSTGRsaW-n9Y7TePBX0AqkkCiTPeS2AhO-hGIug7wE");
    }
}
