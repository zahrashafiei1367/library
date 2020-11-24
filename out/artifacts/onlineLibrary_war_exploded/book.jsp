<%@ page import="dto.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.Book" %><%--
  Created by IntelliJ IDEA.
  User: KOMEIL
  Date: 6/30/2020
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {font-family: Arial, Helvetica, sans-serif;}
        form {border: 3px solid #f1f1f1;}

        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            opacity: 0.8;
        }

        .cancelbtn {
            width: auto;
            padding: 10px 18px;
            background-color: #f44336;
        }

        .imgcontainer {
            text-align: center;
            margin: 24px 0 12px 0;
        }

        img.avatar {
            width: 40%;
            border-radius: 50%;
        }

        .container {
            padding: 16px;
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }

        /* Change styles for span and cancel button on extra small screens */
        @media screen and (max-width: 300px) {
            span.psw {
                display: block;
                float: none;
            }
            .cancelbtn {
                width: 100%;
            }
        }
    </style>
</head>
<body>

<h2>Welcome!</h2>
<form action="adminJob" method="GET">
    <button type="submit" name="id" value="backhome" style="width: 10%;">BACK</button>
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Writer</th>
            <th>Publisher</th>
            <th>Category</th>
        </tr>
        <%
            ArrayList<Book> books =
                    (ArrayList<Book>)request.getAttribute("books");
            for(Book book:books){%>
        <tr>
            <td><%=book.getId()%></td>
            <td><%=book.getName()%></td>
            <td><%=book.getWriter()%></td>
            <td><%=book.getPublisher()%></td>
            <td><%=book.getCategory()%></td>
            <td><c:if test="<%=book.getAvailable()%>">
                    <input type="checkbox" checked>
            </c:if>
                <c:if test="!<%=book.getAvailable()%>">
                    <input type="checkbox">
                </c:if>

    </td>
            <td><button type="submit" name="id" value="editbook<%=book.getId()%>">Edit</button></td>
            <%--                    <a href='' onclick="this.href='http://localhost:8080/onlineLibrary_war_exploded/adminJob?id=edituser&idid=<%=u.getId()%>&name='+document.getElementById('userName').value+'&family='+document.getElementById('userFamily').value+'&username='+document.getElementById('userUsername').value+'&password='+document.getElementById('userPassword').value">Edit</a></td>--%>
            <td><button type="submit" name="id" value="deletebook<%=book.getId()%>">Delete</button></td>
        </tr>
        <%}%>

    </table>
    <table>
        <tr>
            <td>
                <table>
                    <tr>
                        <th>BOOK NAME</th>
                        <th>BOOK WRITER</th>
                        <th>BOOK PUBLISHER</th>
                        <th>BOOK CATEGORY</th>
                    </tr>
                    <tr>
                        <td> <input type="text" id="bookName" name="bookName"></td>
                        <td><input type="text" id="bookWriter" name="bookWriter" ></td>
                        <td><input type="text" id="bookPublisher" name="bookPublisher" ></td>
                        <td><input type="text" id="bookCategory" name="bookCategory" ></td>
                    </tr>
                </table></td>
            <td><input type="checkbox" id="isAvailable" name="isAvailable">
                <h>is available?</h></td>
            <td>
                <button type="submit" name="id" value="addBook">ADD</button>
            </td>
        </tr>
    </table>
    <%--        <label><b>MEMBER NAME</b></label>--%>
    <%--        <input type="text" id="userName" name="userName">--%>
    <%--        <label><b>MEMBER Family</b></label>--%>
    <%--        <input type="text" id="userFamily" name="userFamily" >--%>
    <%--        <label><b>MEMBER USERNAME</b></label>--%>
    <%--        <input type="text" id="userUsername" name="userUsername" >--%>
    <%--        <label><b>MEMBER PASSWORD</b></label>--%>
    <%--        <input type="text" id="userPassword" name="userPassword" >--%>
    <%--        <input type="checkbox" id="isAdmin" name="isAdmin">--%>
    <%--    <h>is admin?</h>--%>
    <%--    <button type="submit" name="id" value="addUser">ADD</button>--%>
</form>

</body>
</html>
