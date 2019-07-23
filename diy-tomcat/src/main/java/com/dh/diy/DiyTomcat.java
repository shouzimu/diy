package com.dh.diy;

public class DiyTomcat {

    //javaee标准
    //servlet
    //request
    //response

    //1、配置好启动端口 默认8080
    //2、配置web.xml
    //servlet-class 自己写的servlet继承HttpServlet
    //servlet-name
    //url-pattern

    //3、读取配置 建立url-pattern和servlet的映射关系
    //Map servlet-Mapping

    //4、Http请求 发送的数据是字符串 有规律的字符串（Http协议）
    //5、从协议内容中拿到URL，把对应的servlet用反射进行实例化
    //6、调用实例化对象的service()方法，执行具体的doGet() doPost()方法
    //7、Request(InputStream)/Response(OutputStream) 请求/返回



}
