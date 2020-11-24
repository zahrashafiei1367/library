<%--
  Created by IntelliJ IDEA.
  User: KOMEIL
  Date: 6/30/2020
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            width: 20%;
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
                width: 15%;
            }
        }
    </style>
    <script>
        function setId() {
            document.getElementById("id").value = document.getElementById("field1").value;
        }
    </script>
</head>
<body>

<h2>Welcome</h2>
<h2 name="username"></h2>
<form action="adminJob" method="GET">
    <button type="submit" name="id" value="user">USERS</button>
    <button type="submit" name="id" value="book">BOOKS</button>
    <br>
    <button type="submit" name="id" value="borrow">BORROW BOOK</button>
    <button type="submit" name="id" value="return">RETURN BOOK</button>
    <br>
    <button type="submit" name="id" value="reportbooks">BORROWED BOOKS REPORT</button>
    <button type="submit" name="id" value="reportintrestreport">INTEREST STATISTICS REPORT</button>
</form>

</body>
</html>
