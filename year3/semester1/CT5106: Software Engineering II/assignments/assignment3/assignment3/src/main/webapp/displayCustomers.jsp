<%-- 
    Document   : displayCustomers
    Created on : 28 Sep 2023, 20:46:42
    Author     : andrew
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display Customers</title>
        <style>
                table, th, td {
                    border: 1px solid black;
                }
       </style>
    </head>
    <body>
        <h1>Our Customers</h1>
        <table>
            <thead>
                <tr>
                    <td>Customer Number</td>
                    <td>Name</td>
                    <td>Phone Number</td>
                    <td>Email</td>
                    <td>Country</td>
                    <td>Postcode</td>
                    <td>Credit Limit</td>
                </tr>
            </thead>
                <c:forEach var="c" items="${customers}">
                    <tr>
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td>${c.phone}</td>
                    <td>${c.email}</td>
                    <td>${c.country}</td>
                    <td>${c.postcode}</td>
                    <td>${c.creditLimit}</td>
                    </tr>
                </c:forEach>
        </table>
    </body>
</html>

