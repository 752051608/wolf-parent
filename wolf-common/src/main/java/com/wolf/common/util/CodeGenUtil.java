package com.wolf.common.util;

import java.util.Random;

/**
 * 券码
 * @author zhanghong
 */
public class CodeGenUtil {

    /** 自定义进制(0,1没有加入,容易与o,l混淆) */
    private static final char[] r = new char[]
            {'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9',
             'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u',
             'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'
            };
    /** (不能与自定义进制有重复) */
    private static final char b = 'o';
    /** 进制长度 */
    private static final int binLen = r.length;
    /** 序列最小长度 */
    private static final int s = 10;

    /**
     * 根据ID生成多少位随机码
     * @param id 用户id
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        char[] buf=new char[32];
        int charPos=32;
        while((id / binLen) > 0) {
            int ind=(int)(id % binLen);
            // System.out.println(num + "-->" + ind);
            buf[--charPos]=r[ind];
            id /= binLen;
        }
        buf[--charPos]=r[(int)(id % binLen)];
        String str=new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if(str.length() < s) {
            StringBuilder sb=new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for(int i=1; i < s - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str+=sb.toString();
        }
        return str;
    }
}
