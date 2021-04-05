<%-- 
    Document   : create
    Created on : Jan 14, 2021, 11:17:23 PM
    Author     : vuong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Page</title>
    </head>
    <body>
        <c:set var = "listType" scope = "page" value = "${sessionScope.LIST_TYPE}"/>
        <form action="MainController" method="POST">
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
                    Type Product: <select name="cbbType" >
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
            <mark>${requestScope.ERROR.typeIDError}</mark></br>
            Product Name: <input type="text" name="txtProductName" value="${product.productName}"/>
            <mark>${requestScope.ERROR.productNameError}</mark></br>
            Price: <input type="text" name="txtPrice" value="${product.price}"/>
            <mark>${requestScope.ERROR.priceError}</mark></br>
            Describe: <input type="text" name="txtDescribe" value="${product.describe}"/>
            <mark>${requestScope.ERROR.describeError}</mark></br>
            Upload Image: <input type="file" name="fileImage" accept="image/png, image/jpeg"/>
            <mark>${requestScope.ERROR.imageError}</mark></br>
            Quantity: <input type="text" name="txtQuantity" value="${product.quantity}"/>
            <mark>${requestScope.ERROR.quantityError}</mark></br>
            <input type="submit" name="btnAction" value="Create"/>
            <input type="reset" value="Reset">
        </form>
    </body>
</html>
