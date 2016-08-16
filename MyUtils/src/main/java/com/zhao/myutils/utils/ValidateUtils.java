package com.zhao.myutils.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    // 用于匹配手机号码
    private static final String REGEX_MOBILE_PHONE =
            "^(0|\\+?86-?|17951)?((13[0-9])|(15[^4,\\D])|(14[57])|(18[0-9])|(17[678]))\\d{8}$";
    // 用于匹配固定电话号码
    private static final String REGEX_DIDPHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
    // 用于匹配400电话
    private static final String REGEX_FOUR_HUNDERD_PHONE = "^400\\d{7}$";

    // 用于匹配100、955等电话
    private static final String REGEX_SPEICAL_PHONE = "^955\\d{2}$";
    private static final String REGEX_SPEICAL_TWO_PHONE = "^(10|11|12)\\d{3,7}";

    // 用于匹配邮箱
    private final static String REGEX_EMAIL =
            "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    // 用于验证URL
    private static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    // 用于验证IP地址
    private static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    // 用于匹配数字
    private static final String REGEX_NUMBER = "[0-9]*";

    // 用于匹配中文字符
    private static final String REGEX_CHINESE = "^[\u4E00-\u9FA5]+$";

    // 用于验证字符串是否是日期
    private static final String REGEX_DATE = "^((\\\\d{2}(([02468][048])|([13579][26]))" +
            "[\\\\-\\\\/\\\\s]?((((0?[13578])|(1[02]))[\\\\-\\\\/\\\\s]?" +
            "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\\\-\\\\/\\\\s]?" +
            "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\\\-\\\\/\\\\s]?((0?[1-9])|" +
            "([1-2][0-9])))))|(\\\\d{2}(([02468][1235679])|([13579][01345789]))[\\\\-\\\\/\\\\s]?" +
            "((((0?[13578])|(1[02]))[\\\\-\\\\/\\\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +
            "[\\\\-\\\\/\\\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\\\-\\\\/\\\\s]?" +
            "((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\\\s(((0?[0-9])|" +
            "([1-2][0-3]))\\\\:([0-5]?[0-9])((\\\\s)|(\\\\:([0-5]?[0-9])))))?$";

    /**
     * 用于验证电话号码，包括手机、座机、400电话等
     */
    public static boolean isPhoneNo(String numbers) {
        return isMobileNO(numbers) || isDidPhoneNO(numbers)
                || isFourHundredPhone(numbers) || isSpecialPhone(numbers)
                || isSpecialTwoPhone(numbers);
    }

    /**
     * 验证是否是手机号
     */
    public static boolean isMobileNO(String mobiles) {
        if (StringUtil.isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_MOBILE_PHONE);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是座机号
     */
    public static boolean isDidPhoneNO(String mobiles) {
        if (StringUtil.isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_DIDPHONE);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是400电话
     */
    public static boolean isFourHundredPhone(String fourHundredNo) {
        if (StringUtil.isBlank(fourHundredNo)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_FOUR_HUNDERD_PHONE);
        Matcher m = p.matcher(fourHundredNo);
        return m.matches();
    }

    /**
     * 用于匹配100、955等电话
     */
    public static boolean isSpecialPhone(String numbers) {
        if (StringUtil.isBlank(numbers)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_SPEICAL_PHONE);
        Matcher m = p.matcher(numbers);
        return m.matches();
    }

    /**
     * 用于匹配11\12\10开头的电话
     */
    public static boolean isSpecialTwoPhone(String numbers) {
        if (StringUtil.isBlank(numbers)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_SPEICAL_TWO_PHONE);
        Matcher m = p.matcher(numbers);
        return m.matches();
    }

    /**
     * 验证是否是邮箱
     */
    public static boolean isEmail(String content) {
        return !StringUtil.isBlank(content) && content.matches(REGEX_EMAIL);
    }

    /**
     * 验证中文
     */
    public static boolean isChinese(String chinese) {
        Pattern p = Pattern.compile(REGEX_CHINESE);
        Matcher m = p.matcher(chinese);
        return m.matches();
    }

    // 完整的判断中文汉字和符号
    public static boolean isAllChinese(String strName) {
        if (strName == null) {
            return false;
        }
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 校验URL
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 判断一个字符串是否都为数字
     */
    public static boolean isDigit(String strNum) {
        return !StringUtil.isBlank(strNum) && strNum.matches(REGEX_NUMBER);
    }

    /**
     * 功能：判断字符串是否为日期格式
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern.compile(REGEX_DATE);
        Matcher m = pattern.matcher(strDate);
        return m.matches();
    }

}
