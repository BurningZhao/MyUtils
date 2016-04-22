# MyUtils
常用工具类
**CountDown.java的使用**
只需在程序中调用简简单单的一行代码即可实现倒计时功能，当然也可以添加监听事件
new CountDown(textView, "%s秒", 10).start();

**DoubleClickExitDetector的使用**
 DoubleClickExitDetector exitDetector = new DoubleClickExitDetector(this);
 @Override
    public void onBackPressed() {
        if (exitDetector.onClick()) {
            super.onBackPressed();
        }
    }

**单个dex最多65535个方法数的瓶颈**
Gradle已经帮我们处理好了，而添加的方法也很简单，总共就分三步
1)首先是在defaultConfig节点使能多DEX功能
android {
        defaultConfig {
            // dex突破65535的限制
            multiDexEnabled true
        }
    }
2)然后就是引入multidex库文件
dependencies {
         compile 'com.android.support:multidex:1.0.0'
    }
3)最后就是你的AppApplication继承一下MultiDexApplication即可

