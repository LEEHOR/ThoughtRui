package com.coahr.thoughtrui.Utils;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.coahr.thoughtrui.mvp.model.ApiContact;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/9
 * 描述：
 */
public class OSS_Aliyun {

    private static OSS_Aliyun aliyun;
    private static OSSCredentialProvider credentialProvider;
    private  static OSSClient ossClient;

    public static OSS_Aliyun newInstance() {
        if (aliyun == null) {
            aliyun = new OSS_Aliyun();
        }
        return aliyun;
    }

    public static OSSClient getOss(Context context) {
        if (credentialProvider == null) {
            credentialProvider = new OSSAuthCredentialsProvider(ApiContact.STSSERVER);
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
            ossClient = new OSSClient(context, ApiContact.endpoint, credentialProvider, conf);
        }
        return ossClient;
    }
}
