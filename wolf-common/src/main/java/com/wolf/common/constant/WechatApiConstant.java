package com.wolf.common.constant;

/**
 * 微信api接口url常量
 */
public final class WechatApiConstant {

    private WechatApiConstant(){}

    /**
     * 微信接口通用grant_type
     */
    public static final String GRANT_TYPE = "client_credential";

    /**
     * 会员端小程序配置的type
     */
    public static final String SMALL_PROGRAM_SETTING_CUSTOMER = "small_program_setting_customer";

    /**
     * 获取access_token接口url
     */
    public  static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s";

    /**
     * 通过该接口生成的小程序码，永久有效，数量暂无限制
     */
    public static final String GET_WX_A_CODE_UNLIMIT = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

    /**
     * 通过该接口获取小程序openId
     */
    public static final String GET_MINIPROGRAM_OPENID = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    /**
     * 发送订阅消息
     */
    public static final String MINIPROGRAM_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";

}
