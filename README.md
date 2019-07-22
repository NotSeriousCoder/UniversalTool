# UniversalTool
通用工具集成库

## 引用方法
### 1.在项目的Gradle文件
	allprojects {
		repositories {
			.......
			maven { url 'https://jitpack.io' }
		}
	}
  
### 2.在你要用这个控件的模块gradle文件
	implementation 'com.github.NotSeriousCoder:UniversalTool:{lastversion}'
	lastversion请看release

## 使用方法

## KeyBoardUtil--控制软键盘

## ScreenUtil--屏幕相关工具类
   ###### getScreenSize--获取屏幕尺寸
   ###### getScreenOrientation--获取屏幕方向（横竖）
   ###### getScreenRotation--获取屏幕方向（4向）

## TimeCounter--计时器
   #### 1.初步使用
        TimeCounter.Builder.createDefaultCounter(View).start()  创建默认计时器，所有参数默认，时长60s
   #### 或者
        TimeCounter.Builder(60, ctv_get_ver_code)
          .setTextCounting("在秒后重新获取") //计数文字
          .setCountPosition(1)    //倒计时秒数在计数文字的出现位置，比方这里，就是“在xx秒后重新获取”
          .setTextDefault("获取验证码")//默认文字
          .setTextPreCount("正在获取验证码")//倒数前文字
          .setTextRetry("重新获取验证码")//一般用不着，预留
          .create()
          .start();
   #### 2.如何暂停计时及恢复计时
        暂停：timeCounter!!.onPause()
        恢复：TimeCounter.Builder.restart(ctv_get_ver_code)
	
## TakePictureUtil--获取图片工具
   ####	1.使用方法
	
	//7.0以上先配置FileProvider
	//可以参考https://www.jianshu.com/p/55eae30d133c
	UriUtil.FILE_PROVIDER_AUTHORITIES = 你自己的authorities
	
	//选择本地图片
	//cut：true==裁剪 false==不裁减
	TakePictureUtil.pickPhotoLocal(true, activity);
	
	//拍照
	//cut：true==裁剪 false==不裁减
	//file：指定存放拍照图片的地址
	TakePictureUtil.takePhotoCamera(true, file, activity);
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//接收结果
		//不裁减的话，target不用传
		//裁剪的话，传入target，target是接收裁减后图像的File
		File image = TakePictureUtil.handleResult(activity, requestCode, resultCode, data, target);
		...
	}

## Log--打Log
   ####	1.使用方法
   
   	//和系统的Log差不多，不过把TAG声明为静态变量，全局初始化即可，也可以临时传入
	//可以设置isDebug，false的时候Log不输出
	Log.TAG = "TEST";
        Log.isDebug = true;
        Log.v("test");
        Log.d("test");
        Log.i("test");
        Log.w("test");
        Log.e("test");
        Log.wtf("test");

        Log.v("LALA", "test");
        Log.d("LALA", "test");
        Log.i("LALA", "test");
        Log.w("LALA", "test");
        Log.e("LALA", "test");
        Log.wtf("LALA", "test");
	
