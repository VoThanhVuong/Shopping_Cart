<%-- 
    Document   : index
    Created on : Jan 8, 2021, 7:58:19 AM
    Author     : vuong
--%>

<%@page import="vuong.dtos.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index Page</title>
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
            <a href="MainController?btnAction=cart">Your Cart</a>
        </c:if>
        <c:if test="${user!=null}">
            <h2>Welcome: <c:out value= "${user.userName}"/></h2>
            <a href="MainController?btnAction=logout">Logout</a>
            <a href="MainController?btnAction=cart">Your Cart</a>
            <a href="history.jsp">Your History</a>
        </c:if>
        <form action="MainController">
            <input type="text" name="txtNamePro"/>
            <c:set var = "listType" scope = "page" value = "${sessionScope.LIST_TYPE}"/>
            <c:if test="${listType!=null}">
                <c:if test="${not empty listType}">
                    <c:set var="typeID" scope="page" value="${param.cbbType}"/>
                    <c:if test="${typeID==null}">
                        <c:set var="typeID" scope="page" value="%"/>
                    </c:if>            
                    <c:if test="${!typeID==null}">
                        <c:set var="typeID" scope="page" value="${cbbType}"/>
                    </c:if>
                    <c:set var="typeName" scope="page" value=""/>

                    <c:forEach var="type" varStatus="counter" items="${listType}">
                        <c:if test="${type.typeID.equalsIgnoreCase(typeID)}">
                            <c:set var="typeName" scope="page" value="${type.typeName}"/>
                        </c:if>    
                    </c:forEach>
                    Choose Type Room: <select name="cbbType" >
                        <c:if test="${typeID.equalsIgnoreCase('%')}">
                            <option value="%">---Choose Type---</option>
                        </c:if>
                        <c:if test="${!typeID.equalsIgnoreCase('%')}">
                            <option value="${typeID}">${typeName}</option>
                            <option value="%">---Choose Type---</option>
                        </c:if>    
                        <c:forEach var="type" varStatus="counter" items="${listType}">
                            <c:if test="${!type.typeID.equalsIgnoreCase(typeID)}">
                                <option value="${type.typeID}"><c:out value="${type.typeName}"/></option>
                            </c:if>    
                        </c:forEach>
                    </select>
                </c:if>
            </c:if> 
            <c:set var = "price" scope = "page" value = "${requestScope.rangePrice}"/>
            <c:if test="${price==null}"> 
                <c:set var = "price" scope="page" value = "100000"/>
            </c:if>
            0đ <input type="range" max="100000" min="1000" step="1000" name="rangePrice" value="${price}"/> 100.000đ
            <input type="submit" name ="btnAction" value="search"/>
        </form>
        <c:set var="listProduct" scope = "page" value="${sessionScope.LIST}"/>
        <c:if test="${listProduct==null}">
            <c:set var="listProduct" scope = "page" value="${sessionScope.LIST_PRODUCT}"/>
        </c:if>
        <table border="1">
            <tbody>
                <c:forEach var="product" varStatus="counter" items="${listProduct}">
                <form action="MainController">
                    <tr>
                        <td>${(sessionScope.PAGE-1)*3+counter.count}</td>
                        <td><img src="${product.image}" alt="${product.productID}" width="150px" height="150px"/></td>
                        <td><h3>${product.productName}</h3></td>
                        <td> <c:if test="${listType!=null}">
                                <c:forEach var="type" varStatus="counter" items="${sessionScope.LIST_TYPE}">
                                    <c:if test="${type.typeID.equalsIgnoreCase(product.typeID)}">
                                        <i><c:out value="${type.typeName}"/></i>
                                    </c:if>    
                                </c:forEach>
                            </c:if>  </td>
                        <td>${product.describe}</td>
                        <td><b>${product.price}đ</b></td>
                        <td>Quantity: <input type="number" name="txtQuantity" max="${product.quantity}" value="1" min="1"/></td>
                        <td><input type="hidden" name="productID" value="${product.productID}"/>
                            <input type="submit" name="btnAction" value="ADD TO CART"/></td>
                    </tr>
                </form>
            </c:forEach>
        </tbody>
    </table>
    <form action="PagingController">
        <input type="submit" name="page" value="<"/> <input type="submit" name="page" value=">"/>
    </form>
</body>
</html>
