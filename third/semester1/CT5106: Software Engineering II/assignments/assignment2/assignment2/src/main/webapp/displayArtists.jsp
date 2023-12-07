<%-- 
    Document   : displayArtists
    Created on : 24 Sep 2023, 11:22:59
    Author     : andrew
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display Artists</title>
        <style>
                table, th, td {
                    border: 1px solid black;
                }
       </style>
    </head>
    <body>
        <h1>Summary of Artists Information</h1>
            <table>
                <tr>
                    <th>Surname</th>
                    <th>First Name</th>
                    <th>Nationality</th>
                    <th>Birth Year</th>
                    <th>Death Year</th>
                </tr>
                <c:forEach var="artist" items="${artistList}">
                    <tr>
                        <td>${artist.surname}</td>
                        <td>${artist.firstname}</td>
                        <td>${artist.nationality}</td>
                        <td>${artist.birthYear}</td>
                        <td>${artist.deathYear}</td>
                        <td>
                            <!-- using form with hidden types to submit artist details to the JSP page -->
                            <form action="displayArtistDetails.jsp" method="post">
                                <input type="hidden" name="surname" value="${artist.surname}">
                                <input type="hidden" name="firstname" value="${artist.firstname}">
                                <input type="hidden" name="nationality" value="${artist.nationality}">
                                <input type="hidden" name="birthYear" value="${artist.birthYear}">
                                <input type="hidden" name="deathYear" value="${artist.deathYear}">
                                <input type="hidden" name="biography" value="${artist.biography}">
                                <input type="hidden" name="photoUrl" value="${artist.photoUrl}">
                                <input type="submit" name="submit" value="Details">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        <a href="addArtist.html">Add Artist</a>
    </body>
</html>
