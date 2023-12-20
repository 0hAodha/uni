<%-- 
    Document   : displayCustomer
    Created on : 28 Sep 2023, 20:16:21
    Author     : andrew
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display Customer</title>
        <style>
                table, th, td {
                    border: 1px solid black;
                }
       </style>

    </head>
    <body>
        <h1>Customer Found</h1>
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
                <tr>
                    <td>${customer.id}</td>
                    <td>${customer.name}</td>
                    <td>${customer.phone}</td>
                    <td>${customer.email}</td>
                    <td>${customer.country}</td>
                    <td>${customer.postcode}</td>
                    <td>${customer.creditLimit}</td>
                </tr>    
        </table>
    </body>
</html>
