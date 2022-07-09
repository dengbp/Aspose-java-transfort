# Aspose-java-transfort
Word, Excel, PowerPoint, PPT,图片,PDF等文档格式相互转换

为什么要破解
官方提供免费的试用，转换出来是带有水印和次数限制的。所以想要绕过许可的困扰。只有两个办法：购买和破解。

从官方的更新频率来看，基本上一个月左右一个版本，所说官方比较重视并重点投入这个项目，同时也说明软件存在待优化的bug。其实在21版本之前有很多转换上的兼容问题，如：

布局错位、特殊符号无法识别，表格内容错乱等造成转换后文档阅读障碍。对于内容要求搞的使用场景来说，无法使用在生产上。

以上主要对最新的22版本进行破解(笔者破解的时候官方发布最新的是22.3版本)

破解方法
破解思路：每个工具包都有对应的License类，这个类是检验许可和授权的入口。从该类入手，慢慢研究反编译后的源码，但其实它使用了混淆手断，使得反编译后的代码看起来比较吃力。
即使如此，对于破解并不是干扰太大，我们不需要去弄清楚每一行，甚至每个方法的功能。破解的任务是移除校验授权对应的代码块或方法。

最简单也是最有效的办法就是直接删除，在这里羚羊就不一一带入分析源码的坑了。下面直接提供现成的包。

破解工具：源码弄不下来，不能像老套路一样，修改对应源码后再对着整个套源码重新编译一遍。怎么办？只能对着目标class文件直接修改保存并覆盖原class文件。在这羚羊推荐三个比较方便使用的工具：
一、JByteMod

这个是界面化工具，修改比较方便，直接把目标jar打开，找到目标的类方法，然后对着要删除的操作指令右键删除保存即可
<img width="359" alt="image" src="https://user-images.githubusercontent.com/9836343/178095826-3f5f1f50-0445-4388-b421-6dd182f05327.png">

二、javassist

界面化的JByteMod，虽然小白都可以使用。但有时候你删除不对，甚至从直观上删除操作指令后没有破坏整个class文件，但是却无法使用，比如报方法不存在、class检验异常等。遇到这种情况的时候，使用javassist就灵活，成功率高很多。如：

public void testMod_words1(){
        try {
            ClassPool.getDefault().insertClassPath("/Users/dengbp/Downloads/doc-sys/pdf-transfer/src/lib/aspose-words-22.3.0-jdk17.jar");
            CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.words.zzP6");
            CtMethod methodB = zzZJJClass.getDeclaredMethod("zzWMy");
            methodB.setBody("{return 256;}");
            zzZJJClass.writeFile("/Users/dengbp/Downloads/");
        } catch (Exception e) {
            System.out.println("错误==" + e);
        }
    }
三、asm

javassist是基于java编程去修改源码内容，而相对于asm，它跟界面化的JByteMod一样，都是直接对jvm的指令去修改。如：

 <img width="571" alt="image" src="https://user-images.githubusercontent.com/9836343/178095818-e8380a3b-77f1-4084-b6b3-24de70a02d72.png">


成品贡献(22.3版本)

<img width="557" alt="image" src="https://user-images.githubusercontent.com/9836343/178095808-db64ea66-b84f-45ba-8529-348609610c37.png">


资源链接: https://pan.baidu.com/s/1XRCaNuXKMLuSQ0p5gib3wg 
