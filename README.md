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


