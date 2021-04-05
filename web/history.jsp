<%-- 
    Document   : history
    Created on : Jan 19, 2021, 7:43:19 AM
    Author     : vuong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
    </head>
    <body>
        <c:set var="mess" scope="page" value="${requestScope.NOTIFY}"/>
        <c:if test="${mess!=null}">
            <script>alert("${mess}");</script>
        </c:if>
        <h1>Hana Shopping</h1>
        <c:set var = "user" scope = "page" value = "${sessionScope.USER}"/>
        <c:if test="${user!=null}">
            <h2>Welcome: <c:out value= "${user.userName}"/></h2>
            <a href="MainController?btnAction=logout">Logout</a>
            <a href="MainController?btnAction=cart">Your Cart</a>
            <a href="index.jsp">Order more</a>
            <h3>Your history: </h3>
            <c:set var="listDate" scope = "page" value="${sessionScope.LIST_DATE}"/>
            <c:if test="${listDate!=null}">
                <c:if test="${not empty listDate}">
                    <form action="MainController">
                        <input type="text" name="txtNamePro"/>
                        Choose Date: <select name="cbbDate" >
                            <option value="%">---Choose Date---</option>
                            <c:forEach var="date" varStatus="counter" items="${sessionScope.LIST_DATE.entrySet()}">
                                <option value="${date.getKey()}"><c:out value="${date.getKey()}"/></option>  
                            </c:forEach>
                        </select> 
                        <input type="submit" name ="btnAction" value="Search"/>
                    </form>
                </c:if>
            </c:if>
            </br>
            <c:set var="history" scope = "page" value="${sessionScope.LIST_HISTORY}"/>
            <c:if test="${history==null}">
                <c:set var="history" scope = "page" value="${sessionScope.HISTORY}"/>
            </c:if>
            <c:if test="${history!=null}">
                <c:if test="${not empty history.getCart().values()}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Product Name</th>
                                <th>Date Order</th>
                                <th>Type</th>
                                <th>Image</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Money</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="detail" varStatus="counter" items="${history.getCart().values()}">
                                <c:set var="dto" scope = "page" value="${detail.getProduct(detail.productID)}"/>
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>${dto.productName}</td>
                                    <td>${detail.getDate()}</td>
                                    <td>${dto.typeID}</td>
                                    <td><img src="${dto.image}" alt="${dto.productID}" width="100px" height="100px"/></td>
                                    <td>${dto.price}</td>
                                    <td>${detail.quantity}</td>
                                    <td>${detail.getIntoMoney()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </c:if>
        </c:if>
    </body>
</html>
