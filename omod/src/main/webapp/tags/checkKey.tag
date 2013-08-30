<%@ attribute name="checkName" required="true" type="java.lang.String" %>
<%@ attribute name="checkId" required="true" type="java.lang.String" %>
<%
        String[] split=checkName.split("\\s");
        String key="";
        for(String s:split) {
            if(s.length()>0) {
            key=key+s.substring(0,1);
            }
        }
        key=key.replaceAll("[^A-Za-z]","");
        key=key.toUpperCase();
        key=checkId+"_"+key;
        out.println(key);
%>