<%-- 
    Document   : pay
    Created on : Jan 22, 2021, 11:06:42 AM
    Author     : vuong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pay Page</title>
    </head>
    <body>
        <h1>Hana Shopping</h1>
        <c:set var = "user" scope = "page" value = "${sessionScope.USER}"/>
        <c:if test="${user!=null}">
            <h2>Welcome: <c:out value= "${user.userName}"/></h2>
            <a href="MainController?btnAction=logout">Logout</a>
            <a href="MainController?btnAction=cart">Your Cart</a>
            <a href="index.jsp">Order more</a>
            <c:set var="listDetail" scope = "page" value="${sessionScope.RECOM}"/>
            <c:set var = "listType" scope = "page" value = "${sessionScope.LIST_TYPE}"/>
            <c:if test="${listDetail!=null}">
                <c:if test="${not empty listDetail.getCart()}">
                    <table border="1">
                        <tbody>
                            <c:forEach var="detail" varStatus="counter" items="${listDetail.getCart().values()}">
                                <c:set var="dto" scope = "page" value="${detail.getProduct(detail.productID)}"/>
                            <form action="MainController">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td><img src="${dto.image}" alt="${detail.productID}" width="150px" height="150px"/></td>
                                    <td><h3>${dto.productName}</h3></td>
                                    <td> <c:if test="${listType!=null}">
                                            <c:forEach var="type" varStatus="counter" items="${sessionScope.listType}">
                                                <c:if test="${type.typeID.equalsIgnoreCase(dto.typeID)}">
                                                 <i><c:out value="${type.typeName}"/></i>
                                                </c:if>    
                                            </c:forEach>
                                        </c:if>  </td>
                                    <td>${dto.describe}</td>
                                    <td><b>${dto.price}đ</b></td>
                                    <td>Quantity: <input type="number" name="txtQuantity" max="${detail.quantity}" value="1" min="1"/></td>
                                    <td><input type="hidden" name="productID" value="${detail.productID}"/>
                                        <input type="submit" name="btnAction" value="ADD TO CART"/></td>
                                </tr>
                            </form>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </c:if>
        <h3>Total money for your order: <c:out value="${requestScope.total}"/></h3>
        <form action="MainController">
            <input type="submit" name="btnAction" value="CONFIRM AND CASH PAYMENT">
        </form>
        <form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="POST">
            <input type="hidden" name="business" value="sb-e75ik4818206@personal.example.com">
            <input type="hidden" name="cmd" value="_xclick">
            <input type="hidden" name="item_name" value="HoaDonMuaHang">
            <input type="hidden" name="amount" value="${total/20000}">
            <input type="hidden" name="currency_code" value="USD">
            <input type="hidden" name="return" value="http://localhost:8084/Lab01/CartController">
            <input type="hidden" name="cancel_return" value="http://localhost:8084/Lab01/pay.jsp">
            <input type="submit" name="btnAction" value="CONFIRM AND PAYPAL PAYMENT">
        </form>
    </c:if>
</body>
</html>
