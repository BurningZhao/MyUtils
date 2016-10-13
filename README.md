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
[参考](https://github.com/AndroidKnife/proguard-config)
[参考](http://blog.hwangjr.com/2015/12/03/ProGuard-%E7%B2%BE%E7%B2%B9/)

# APK瘦身
(https://zhuanlan.zhihu.com/p/21962184)

# EmptyService和WeakAsyncTask的使用
EmptyService ：
Background Service that is used to keep our process alive long enough for background threads
to finish. Started and stopped directly by specific background tasks when needed.
        <service
            android:name=".common.util.EmptyService"
            android:exported="false" />

   new UpdateTask(this).execute(diff);
         
            /**
                * Background task that persists changes to {@link Groups#GROUP_VISIBLE},
                * showing spinner dialog to user while updating.
                */
               public static class UpdateTask extends
                       WeakAsyncTask<ArrayList<ContentProviderOperation>, Void, Void, Activity> {
                   private ProgressDialog mProgress;
           
                   public UpdateTask(Activity target) {
                       super(target);
                   }
           
                   /** {@inheritDoc} */
                   @Override
                   protected void onPreExecute(Activity target) {
                       final Context context = target;
           
                       mProgress = ProgressDialog.show(
                               context, null, context.getText(R.string.savingDisplayGroups));
           
                       // Before starting this task, start an empty service to protect our
                       // process from being reclaimed by the system.
                       context.startService(new Intent(context, EmptyService.class));
                   }
           
                   /** {@inheritDoc} */
                   @Override
                   protected Void doInBackground(
                           Activity target, ArrayList<ContentProviderOperation>... params) {
                       final Context context = target;
                       final ContentValues values = new ContentValues();
                       final ContentResolver resolver = context.getContentResolver();
           
                       try {
                           final ArrayList<ContentProviderOperation> diff = params[0];
                           resolver.applyBatch(ContactsContract.AUTHORITY, diff);
                       } catch (RemoteException e) {
                           Log.e(TAG, "Problem saving display groups", e);
                       } catch (OperationApplicationException e) {
                           Log.e(TAG, "Problem saving display groups", e);
                       }
           
                       return null;
                   }
           
                   /** {@inheritDoc} */
                   @Override
                   protected void onPostExecute(Activity target, Void result) {
                       final Context context = target;
           
                       try {
                           mProgress.dismiss();
                       } catch (Exception e) {
                           Log.e(TAG, "Error dismissing progress dialog", e);
                       }
           
                       target.finish();
           
                       // Stop the service that was protecting us
                       context.stopService(new Intent(context, EmptyService.class));
                   }
               }

# proguard 配置
[Proguard-Config](https://github.com/AndroidKnife/proguard-config)
可以参考这个库,可以把该库中的相应lib对应的文件,直接copy文件中内容到proguard-rules.pro

# android M 运行时权限
- [简单适配6.0的运行时权限-XPermission](https://github.com/a5533348/XPermission);
- [Android 6.0系统中权限问题调用 - Permission in Android M](https://cankingapp.github.io/2016/03/18/android-permission/)

# 参考学习的开源库
[SystemBarTint](https://github.com/jgilfelt/SystemBarTint)
系统栏管理器，我把它用在ZBLibrary的BaseActivity中实现了状态栏沉浸。
[ZBLibrary](https://github.com/TommyLemon/Android-ZBLibrary)
很多东西是从这个库里面学习copy
