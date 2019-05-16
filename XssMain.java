package com.xss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2017/10/16.
 */
public class XssMain {

//    static String dirStr = "E:\\DO\\77\\xx\\Epay\\pb_cert_downloadLogin.jsp";
//    static String dirStr = "E:\\DO\\77\\xx\\Epay";
    static String dirStr = "E:\\DO\\77\\xx\\WebContent";
//    static String dirStr = "E:\\DO\\77\\xx\\mvcs";

    static void test(){
//        String content = "<%@ include file=\"../no_session_head.jsp\" %>\r\n";
        String content = "<%@ include file=\"./password.jsp\" %>\r\n";
//        Pattern p = Pattern.compile("<%@\\s*include\\s*file=\"\\w*head\\.jsp\"\\s*%>");// 前置条件1
        Pattern p = Pattern.compile("<%@\\s*include\\s*file=\"[\\./\\w]*password\\.jsp\"\\s*%>(?!--)");// 前置条件1
        Matcher m = p.matcher(content);
        while(m.find()) {
            System.out.println(m.group(0));
//            System.out.println(m.group(1));
        }
        content = content.replaceAll(p.pattern(),"<%--$0--%>");
        System.out.println(content);
    }


    public static void main(String[] args) throws Exception{
//        test();

        System.out.println("程序开始。。。。。。");
        scanDir(new File(dirStr));
//        for(String s:sets.toArray(new String[0])){
//            System.out.println(s);
//        }
        System.out.println("程序结束。。。。。。");
    }

    // 递归扫描入口
    public static void scanDir(File dir) throws Exception{
        if(dir.isDirectory()) {
            File[] list = dir.listFiles();
            for(File f : list) {
                if(f.isDirectory()) {
                    scanDir(f);// 目录递归
                }else {
                    doFile(f);// 文件处理
//                    findInclude(f);// 扫描识别出一共有哪些include
                }
            }
        }else {
            doFile(dir);// 文件处理
//            findInclude(dir);// 扫描识别出一共有哪些include
        }
    }

    // 核心处理函数
    public static void doFile(File f){
        try {

            if (!f.getName().endsWith("jsp")) {

                System.out.println("删除文件：" + f.getPath());
                f.delete();

            }else{

                // 跳过的文件
                if(f.getName().equals("netPayhead.jsp") || f.getName().equals("no_session_head.jsp")){
                    f.delete();
                    return;
                }

                System.out.println("处理文件：" + f.getPath());

                // 获取文件内容
                Long fileLength = f.length();
//            byte[] fileBytes = new byte[fileLength.intValue()];
                ByteBuffer fileBuffer = ByteBuffer.allocate(fileLength.intValue());
                byte[] buffer = new byte[1024];
                int readLen = 0;
                FileInputStream in = null;
                String content = null;
                try {
                    in = new FileInputStream(f);
                    while ((readLen = in.read(buffer)) != -1) {
                        fileBuffer.put(buffer, 0, readLen);
                    }
                    fileBuffer.flip();
                    content = new String(fileBuffer.array(), "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    in.close();
                }
                String md5 = new String(MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8")));


                // 开始逐步分类处理
                content = new Xss1().run(content);//<%=webAppPath %>
                content = new Xss2().run(content);//String xxxxx = utb.get("xxxxx");
                content = new Xss3().run(content);//String xxxxx = context.getDataValue("xxxxx");
                //content = new Xss4().run(content);//<%@ include file="netPayhead.jsp" %>
                //content = new Xss5().run(content);//<%@ include file="password.jsp" %>
                content = new Xss6().run(content);//String mobileNo = (String)sessionCtx.get("session_B2EmobileNo");
                content = new Xss7().run(content);//<%=utb.formatCurrencyData(allAmount) %>
                content = new Xss8().run(content);//<%=utb.getRequiredHtmlFields ( true ) %>
                content = new Xss9().run(content);//<%= utb.getContext().get("resultData")%>

                //content = new Xss10().run(content);//utb.initialize(request, response)改成utb.initializeNoSession(request, response)
                //content = new Xss11().run(content);//解决getRequiredHtmlFields和解决getConfirmFields


//            System.out.println(content);

                // 写回文件中
                if (!md5.equals(new String(MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"))))) {
                    System.out.println("重写：" + f.getPath());
                    FileOutputStream out = null;
                    File f2 = new File(f.getPath() + ".sheep");
                    try {
                        out = new FileOutputStream(f2);
                        out.write(content.getBytes("UTF-8"));
                        out.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        out.close();
                    }

                    // 删除旧的，重命名新的
                    f.delete();
                    f2.renameTo(f);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("处理文件异常：" + f.getParent());
        }


    }

    // 查找jsp文件include情况
    private static Set<String> sets = new HashSet<String>();
    public static void findInclude(File f){
        try{
            if (f.getName().endsWith("jsp")) {

                // 获取文件内容
                Long fileLength = f.length();
                ByteBuffer fileBuffer = ByteBuffer.allocate(fileLength.intValue());
                byte[] buffer = new byte[1024];
                int readLen = 0;
                FileInputStream in = null;
                String content = null;
                try {
                    in = new FileInputStream(f);
                    while ((readLen = in.read(buffer)) != -1) {
                        fileBuffer.put(buffer, 0, readLen);
                    }
                    fileBuffer.flip();
                    content = new String(fileBuffer.array(), "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    in.close();
                }

                Pattern p = Pattern.compile("<%@\\s*include\\s*file=\"[^\"]*\"\\s*%>");
                Matcher m = p.matcher(content);
                while(m.find()) {
                    sets.add(m.group(0));
                }
//                if(!m.find()) {
//                    sets.add(f.getPath());
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
