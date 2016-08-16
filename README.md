# MyUtils, 常用工具类
# **CountDown.java的使用**
只需在程序中调用简简单单的一行代码即可实现倒计时功能，当然也可以添加监听事件
new CountDown(textView, "%s秒", 10).start();

# **DoubleClickExitDetector的使用**
 DoubleClickExitDetector exitDetector = new DoubleClickExitDetector(this);
 @Override
    public void onBackPressed() {
        if (exitDetector.onClick()) {
            super.onBackPressed();
        }
    }    

# SSLUtil的使用
[Android HTTPS如何10分钟实现自签名SSL证书](http://my.oschina.net/u/2437072/blog/669041)

# proguard的使用
ProGuard通过删除无用代码，将代码中类名、方法名、属性名用晦涩难懂的名称重命名从而达到代码混淆、压缩和优化的功能
可以使用该链接添加配置信息
[参考文章](https://github.com/AndroidKnife/proguard-config)
[参考文章](http://blog.hwangjr.com/2015/12/03/ProGuard-%E7%B2%BE%E7%B2%B9/)

APK瘦身
https://zhuanlan.zhihu.com/p/21962184