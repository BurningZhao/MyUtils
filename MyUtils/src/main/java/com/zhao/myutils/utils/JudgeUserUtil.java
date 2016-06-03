package com.zhao.myutils.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 登录注册判断
 *
 * @author zhaoqingbo
 * @since 2016/6/1
 */
public class JudgeUserUtil {

    public static boolean judgeusername(Context context, String username) {
        if (!judgeUsernameValid(username)) {
            ToastUtil.showMidToast(context, "用户名格式不正确", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public static boolean validPhoneNum(Context context, String phone){
        if(!ValidateUtils.isMobileNO(phone)){
            ToastUtil.showMidToast(context, "请正确填写11位手机号", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public static boolean judgeUsernameValid(String username) {
        return checkUserNameRegex(username);
    }

    public static boolean judgeEmail(Context context, String email) {
        if(!ValidateUtils.isEmail(email)){
            Toast.makeText(context, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean checkUserNameRegex(String userName){
        String reg = "^[a-zA-Z]{1}\\w{4,19}$";
        return check(reg, userName);
    }

    public static boolean check(String checkReg, String str){
        Pattern pattern = Pattern.compile(checkReg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}