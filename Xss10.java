package com.xss;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss10 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    // session头改成非session
    public String run(String content){
//             10.
        p = Pattern.compile("<%@\\s*include\\s*file=\"[^\"]*\"\\s*%>");
        m = p.matcher(content);
        if(m.find()) {
            String includeStr = m.group(0);// 获得include行
            if(includeStr.indexOf("no_session") != -1){
//                    1.utb.initialize(request, response)改成utb.initializeNoSession(request, response)
                content = content.replaceAll("utb\\.initialize\\(request,\\s*response\\)","utb.initializeNoSession(request, response)");

//                    2.去掉错误和不必要的行
                content = content.replaceAll("[\\s\\n\\r\\t]*response\\.sendRedirect\\(request\\.getContextPath\\(\\)\\s*\\+\\s*\"/errorpage/session_expire.html\"\\);[\\s*\\n\\r\\t]*return;","");
                content = content.replaceAll("[\\s\\n\\r\\t]*pageContext\\.setAttribute\\(\"imgPath\",\\s*utb\\.getImgPath\\(\\)\\);","");
                content = content.replaceAll("[\\s\\n\\r\\t]*pageContext\\.setAttribute\\(\"cssPath\",\\s*utb\\.getCssPath\\(\\)\\);","");
                content = content.replaceAll("[\\s\\n\\r\\t]*pageContext\\.setAttribute\\(\"jsPath\", \\s*utb\\.getJsPath\\(\\)\\);","");
                content = content.replaceAll("[\\s\\n\\r\\t]*pageContext\\.setAttribute\\(\"scriptPath\",\\s*utb\\.getScriptPath\\(\\)\\);","");
                content = content.replaceAll("[\\s\\n\\r\\t]*pageContext\\.setAttribute\\(\"showNumPerPage\",\\s*Integer\\.parseInt\\(utb\\.getDisplayAt\\(\"NUM_SHOWNUM_PERPAGE\",\\s*\"1\"\\)\\)\\);","");
                content = content.replaceAll("[\\s\\n\\r\\t]*pageContext\\.setAttribute\\(\"sessionContext\",\\s*utb.getSessionContext\\(\\)\\);","");
                content = content.replaceAll("[\\s\\n\\r\\t]*Context\\s*sessionCtx\\s*=\\s*utb\\.getSessionContext\\(\\);","");
            }
        }
        return content;
    }
}
