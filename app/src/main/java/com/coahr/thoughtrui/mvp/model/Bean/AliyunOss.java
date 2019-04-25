package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/25
 * 描述：
 */
public class AliyunOss {

    /**
     * AccessKeyId : STS.NJ7oqwb87LWvss2RgBaPBB4aT
     * AccessKeySecret : 4W8FUyX7Tk6d4pZwdEP67DWjqCnxVLNEZsdo5xH6yu9m
     * Expiration : 1554370361000
     * SecurityToken : CAISgwJ1q6Ft5B2yfSjIr4mCJMvDj+cW+5WdcVWDtmcXbd9urfHKtjz2IH5KfXZgAu4Wsv0/n2hS7/4alqB6T55OSAmcNZIofwb6JPXkMeT7oMWQweEuqv/MQBq+aXPS2MvVfJ+KLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/i6/clFKN1ODO1dj1bHtxbCxJ/ocsBTxvrOO2qLwThjxi7biMqmHIl1D0kuPnvkpLEs0GP3AOr8IJP+dSteKrDRtJ3IZJyX+2y2OFLbafb2EZSkUMUqv4p3PcaqWue7orFWQAJuw/1Ou7V48BpKxRieq85FqhLof7xj/RkvfbJkID626Piwm6l9q+1GoABryxGisxZPyVnKWP/25sw2VBlwf3mY/gB0+gDk6w1EC30KuO+WUTSFKowE3bolUwRdetLC9y42laluBMsLhR1sFl4BaHJdhEkYMvWcPdNJ5EL1THcLauc5rreTC44GhChegwK+aeHLH1KQwLmH64lkHiDFg6MvvhB8eDU5ex4laM=
     * StatusCode : 200
     * bucket : three-research
     * region : oss-cn-hangzhou
     */

    private String AccessKeyId;
    private String AccessKeySecret;
    private long Expiration;
    private String SecurityToken;
    private int StatusCode;
    private String bucket;
    private String region;

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String AccessKeyId) {
        this.AccessKeyId = AccessKeyId;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String AccessKeySecret) {
        this.AccessKeySecret = AccessKeySecret;
    }

    public long getExpiration() {
        return Expiration;
    }

    public void setExpiration(long Expiration) {
        this.Expiration = Expiration;
    }

    public String getSecurityToken() {
        return SecurityToken;
    }

    public void setSecurityToken(String SecurityToken) {
        this.SecurityToken = SecurityToken;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
