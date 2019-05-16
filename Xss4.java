package com.xss;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss4 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){

        // 经过遍历搜索，得到的所有include情况
//                mvcs
//                <%@ include file="./head.jsp" %>---session
//                <%@ include file="../ajax/head.jsp" %>---session---ajax
//                <%@ include file="../no_session_head.jsp" %>---no_session
//                <%@ include file="./netPayhead.jsp" %>---session
//                <%@ include file="../netPaySys/netPayhead.jsp" %>---session
//                <%@ include file="./password.jsp" %>---none
//                <%@ include file="../B2EpayMobile/password.jsp" %>---none
//                WebContent
//                <%@ include file="./WEB-INF/mvcs/crpay/no_session_head.jsp" %>---no_session
//                <%@ include file="../WEB-INF/mvcs/crpay/no_session_head.jsp"%>---no_session
//                <%@ include file="./head.jsp"%>---session
//                <%@ include file="./errorpage/head.jsp"%>---session
//                <%@ include file="./noutb_head.jsp"%>---session

//             4.寻找:
//               <%@ include file="no_session_head.jsp" %>
//               <%@ include file="./no_session_head.jsp" %>
//               <%@ include file="../no_session_head.jsp" %>
//               <%@ include file="head.jsp" %>
//               <%@ include file="./head.jsp" %>
//               <%@ include file="../xxxxx/head.jsp" %>
//               <%@ include file="netPayhead.jsp" %>
//               <%@ include file="./netPayhead.jsp" %>
//               <%@ include file="../xxxxx/netPayhead.jsp" %>
//               替换:
//               <%--<%@ include file="no_session_head.jsp" %>--%>
//               <%--<%@ include file="./no_session_head.jsp" %>--%>
//               <%--<%@ include file="../no_session_head.jsp" %>--%>
//               <%--<%@ include file="head.jsp" %>--%>
//               <%--<%@ include file="./head.jsp" %>--%>
//               <%--<%@ include file="../xxxxx/head.jsp" %>--%>
//               <%--<%@ include file="netPayhead.jsp" %>--%>
//               <%--<%@ include file="./netPayhead.jsp" %>--%>
//               <%--<%@ include file="../xxxxx/netPayhead.jsp" %>--%>
//                加上
//                <%@ page import="java.util.Date" %>
//                <%@ page import="com.ecc.emp.core.Context" %>
//                <%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
//                <%@taglib prefix="crbc" uri="com.ecc.liana.crpay.util.tag"%>
//                <%@taglib prefix="ctp" uri="com.ecc.emp"%>
//                <%@taglib prefix="liana" uri="com.ecc.liana.I18N"%>
//                <jsp:useBean id="utb" scope="page" class="com.ecc.liana.crpay.html.PBJspContextServices">
//                <%
//                try{
//                    utb.initialize(request, response);---session时候使用
//                    utb.initializeNoSession(request, response);---no_session时候使用
//                }catch(Exception ex){
//                    response.sendRedirect(request.getContextPath() + "/errorpage/session_expire.html");---no_session时候不能输出
//                    return;---no_session时候不能输出
//                }
//                %>
//                </jsp:useBean>
//                <%
//                pageContext.setAttribute("webAppPath", utb.getWebAppPath());---ajax时候不能输出
//                pageContext.setAttribute("version", new java.text.SimpleDateFormat("yyyyMMdd").format(new Date()));---ajax时候不能输出
//                pageContext.setAttribute("timeMillis", System.currentTimeMillis());---ajax时候不能输出
//                pageContext.setAttribute("imgPath", utb.getImgPath());---no_session|ajax时候不能输出
//                pageContext.setAttribute("cssPath", utb.getCssPath());---no_session|ajax时候不能输出
//                pageContext.setAttribute("jsPath", utb.getJsPath());---no_session|ajax时候不能输出
//                pageContext.setAttribute("scriptPath", utb.getScriptPath());---no_session|ajax时候不能输出
//                pageContext.setAttribute("showNumPerPage", Integer.parseInt(utb.getDisplayAt("NUM_SHOWNUM_PERPAGE", "1")));---no_session|ajax时候不能输出
//                pageContext.setAttribute("sessionContext", utb.getSessionContext());---no_session|ajax时候不能输出
//                Context context = utb.getContext();
//                Context sessionCtx = utb.getSessionContext();---no_session|ajax时候不能输出
//                %>
//                <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">---ajax时候不能输出
        sb = new StringBuilder();
        p = Pattern.compile("<%@\\s*include\\s*file=\"[\\./\\w]*head\\.jsp\"\\s*%>(?!--)");
        m = p.matcher(content);
        if(m.find()) {
            String includeStr = m.group(0);// 获得include行
            boolean ajax,session;
            ajax = includeStr.indexOf("ajax") != -1;
            session = ajax || includeStr.indexOf("no_session") == -1;// 根据实际源码，ajax时候是session的
            sb.append("<%--$0--%>");// 注释掉include导入
            sb.append("\r\n").append("<%@ page import=\"java.util.Date\" %>");
            sb.append("\r\n").append("<%@ page import=\"com.ecc.emp.core.Context\" %>");
            sb.append("\r\n").append("<%@taglib prefix=\"c\" uri=\"http://java.sun.com/jstl/core\"%>");
            if(!ajax) {
                sb.append("\r\n").append("<%@taglib prefix=\"crbc\" uri=\"com.ecc.liana.crpay.util.tag\"%>");
                sb.append("\r\n").append("<%@taglib prefix=\"ctp\" uri=\"com.ecc.emp\"%>");
                sb.append("\r\n").append("<%@taglib prefix=\"liana\" uri=\"com.ecc.liana.I18N\"%>");
            }
            sb.append("\r\n").append("<jsp:useBean id=\"utb\" scope=\"page\" class=\"com.ecc.liana.crpay.html.PBJspContextServices\">");
            sb.append("\r\n").append("<%");
            sb.append("\r\n\t").append("try{");
            if(session) {
                sb.append("\r\n\t\t").append("utb.initialize(request, response);");
            }else {
                sb.append("\r\n\t\t").append("utb.initializeNoSession(request, response);");
            }
            sb.append("\r\n\t").append("}catch(Exception ex){");
            if(session) {
                sb.append("\r\n\t\t").append("response.sendRedirect(request.getContextPath() + \"/errorpage/session_expire.html\");");
                sb.append("\r\n\t\t").append("return;");
            }
            sb.append("\r\n\t").append("}");
            sb.append("\r\n").append("%>");
            sb.append("\r\n").append("</jsp:useBean>");
            sb.append("\r\n").append("<%");
            if(!ajax) {
                sb.append("\r\n\t").append("pageContext.setAttribute(\"webAppPath\", utb.getWebAppPath());");
                sb.append("\r\n\t").append("pageContext.setAttribute(\"version\", new java.text.SimpleDateFormat(\"yyyyMMdd\").format(new Date()));");
                sb.append("\r\n\t").append("pageContext.setAttribute(\"timeMillis\", System.currentTimeMillis());");
                if(session) {
                    sb.append("\r\n\t").append("pageContext.setAttribute(\"imgPath\", utb.getImgPath());");
                    sb.append("\r\n\t").append("pageContext.setAttribute(\"cssPath\", utb.getCssPath());");
                    sb.append("\r\n\t").append("pageContext.setAttribute(\"jsPath\", utb.getJsPath());");
                    sb.append("\r\n\t").append("pageContext.setAttribute(\"scriptPath\", utb.getScriptPath());");
                    sb.append("\r\n\t").append("pageContext.setAttribute(\"showNumPerPage\", Integer.parseInt(utb.getDisplayAt(\"NUM_SHOWNUM_PERPAGE\", \"1\")));");
                    sb.append("\r\n\t").append("pageContext.setAttribute(\"sessionContext\", utb.getSessionContext());");
                }
            }
            sb.append("\r\n\t").append("Context context = utb.getContext();");
            if(session) {
                sb.append("\r\n\t").append("Context sessionCtx = utb.getSessionContext();");
            }
            sb.append("\r\n").append("%>");
            if(!ajax) {
                sb.append("\r\n").append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
            }
            sb.append("\r\n");
            content = content.replaceAll("<%@\\s*include\\s*file=\"[\\./\\w]*head\\.jsp\"\\s*%>(?!--)", sb.toString());
        }
        return content;
    }
}
