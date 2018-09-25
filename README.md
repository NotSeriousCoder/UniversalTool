# UniversalTool
通用工具集成库

## KeyBoardUtil--控制软键盘

##
    ### 1.初步使用
        TimeCounter.Builder.createDefaultCounter(View).start()  创建默认计时器，所有参数默认，时长60s
    ### 或者
        TimeCounter.Builder(60, ctv_get_ver_code)
          .setTextCounting("在秒后重新获取") //计数文字
          .setCountPosition(1)    //倒计时秒数在计数文字的出现位置，比方这里，就是“在xx秒后重新获取”
          .setTextDefault("获取验证码")//默认文字
          .setTextPreCount("正在获取验证码")//倒数前文字
          .setTextRetry("重新获取验证码")//一般用不着，预留
          .create()
          .start();
    ### 2.如何暂停计时及恢复计时
        暂停：timeCounter!!.onPause()
        恢复：TimeCounter.Builder.restart(ctv_get_ver_code)
