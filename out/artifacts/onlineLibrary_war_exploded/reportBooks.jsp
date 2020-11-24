<%@ page import="java.io.FileInputStream" %><%--
  Created by IntelliJ IDEA.
  User: KOMEIL
  Date: 11/24/2020
  Time: 10:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>report</title>
</head>
<body>
<%
    String file = application.getRealPath("/") + "log.out";
    FileInputStream fileinputstream = new FileInputStream(file);

    int numberBytes = fileinputstream.available();
    byte bytearray[] = new byte[numberBytes];

    fileinputstream.read(bytearray);

    for(int i = 0; i < numberBytes; i++){
        out.println((char)bytearray[i]);
    }

    fileinputstream.close();
%>
</body>
</html>
