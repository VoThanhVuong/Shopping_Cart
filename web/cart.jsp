<%-- 
    Document   : cart
    Created on : Jan 16, 2021, 11:43:29 AM
    Author     : vuong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
    </head>
    <body>
        <c:set var="mess" scope="page" value="${requestScope.NOTIFY}"/>
        <c:if test="${mess!=null}">
            <script>alert("${mess}");</script>
        </c:if>
        <h1>Hana Shopping</h1>
        <c:set var = "user" scope = "page" value = "${sessionScope.USER}"/>
        <c:if test="${user==null}">
            <a href="MainController?btnAction=login page">Login</a>
        </c:if>
        <c:if test="${user!=null}">
            <h2>Welcome: <c:out value= "${user.userName}"/></h2>
            <a href="MainController?btnAction=logout">Logout</a>
            <a href="history.jsp">Your History</a>
        </c:if>
        <a href="index.jsp">Order more</a>
        <h3>Your cart moment: </h3>
        <c:set var="cart" scope = "page" value="${sessionScope.CART}"/>
        <c:set var="total" scope = "page" value="${0}"/>
        <c:if test="${cart!=null}">
            <c:if test="${not empty cart.getCart()}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Product Name</th>
                            <th>Type</th>
                            <th>Image</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Money</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="detail" varStatus="counter" items="${cart.getCart().values()}">
                            <c:set var="dto" scope = "page" value="${detail.getProduct(detail.productID)}"/>
                            <c:set var="total" scope = "page" value="${total+detail.getIntoMoney()}"/>
                        <form action="MainController">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.productName}</td>
                                <td>${dto.typeID}</td>
                                <td><img src="${dto.image}" alt="${dto.productID}" width="100px" height="100px"/></td>
                                <td>${dto.price}</td>
                                <td><input type="number" name="txtQuantity" max="${dto.quantity}" value="${detail.quantity}" min="1"/></td>
                                <td>${detail.getIntoMoney()}</td>
                                <td>  
                                    <input type="hidden" name="productID" value="${dto.productID}"/> 
                                    <input type="submit" name="btnAction" value="Update"/>  
                                </td>
                                <td>
                                    <c:url var="delete" value="MainController">
                                        <c:param name="btnAction" value="delete"></c:param>
                                        <c:param name="productID" value="${dto.productID}"></c:param>  
                                    </c:url>
                                    <a href="${delete}" onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </c:if>
    <c:if test="${sessionScope.CART==null||empty sessionScope.CART.getCart()}">
        <h4>Your cart is empty</h4>
    </c:if>
    <c:if test="${sessionScope.CART!=null && not empty sessionScope.CART.getCart()}">
        <h4>Total money your cart: ${total}</h4>
        <form action="MainController">
            <input type="hidden" name="total" value="${total}">
            <input type="submit" name="btnAction" value="CONFIRM AND PAYMENT">
        </form>
    </c:if>
</body>
</html>
