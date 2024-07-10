package com.shixi3.communitybackend.auth.util;

public class MscTools {

    /**
     * 判断一个字符串是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (!Character.isDigit(aChar)) {
                return false;
            }
        }
        return true;
    }

}
