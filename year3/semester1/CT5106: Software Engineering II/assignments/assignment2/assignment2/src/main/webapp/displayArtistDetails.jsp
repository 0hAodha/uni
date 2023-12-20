<%-- 
    Document   : testdisplayArtistDetails
    Created on : 24 Sep 2023, 11:49:41
    Author     : andrew
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${param.firstname} ${param.surname}</title>
    </head>
    <body>
        <h1>Artist Details</h1>
            <h2>${param.firstname} ${param.surname}</h2>
                <img src="${param.photoUrl}" alt="${param.firstname} ${param.surname}">
            <h2>Biography</h2> 
                <p>${param.biography}</p>
            <h2>Details</h2>
                <p>
                    Birth Year: ${param.birthYear} <br>
                    <!-- only displaying the death year if its String is not empty -->
                    <c:if test="${param.deathYear ne ''}">
                        Death Year: ${param.deathYear} <br>
                    </c:if>
                        Nationality: ${param.nationality} <br>
                </p>
    </body>
</html>