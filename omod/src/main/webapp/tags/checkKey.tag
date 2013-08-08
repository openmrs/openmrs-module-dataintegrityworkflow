<%@ attribute name="checkName" required="true" type="java.lang.String" %>
<%@ attribute name="checkId" required="true" type="java.lang.Integer" %>
<%
    String[] split=checkName.split("\\s");
    String key="";
    for(String s:split) {
        key=key+Character.toString(s.charAt(0));
    }
    key=key.replaceAll("[^A-Za-z]", "");
    key=key.toUpperCase();
    key=Integer.parseInt(checkId)+"_"+key;
    out.println(key);
%>