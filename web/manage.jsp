<%-- 
    Document   : manage.jsp
    Created on : Jan 12, 2021, 8:51:14 PM
    Author     : vuong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Page</title>
    </head>
    <body>
        <c:set var="mess" scope="page" value="${requestScope.NOTIFY}"/>
        <c:if test="${mess!=null}">
            <script>alert("${mess}");</script>
        </c:if>
        <h1>Hana Shopping</h1>
        <c:set var = "user" scope = "page" value = "${sessionScope.USER}"/>
        <c:set var = "name"  value = ""/>
        <c:if test="${user.userName!=null}">
            <c:set var = "name" scope = "page" value = "${user.userName}"/>
        </c:if>
        <c:if test="${user.userName==null}">
            <c:set var = "name" scope = "page" value = "Admin"/>
        </c:if>
        <h2>Welcome ${name} </h2>
        <a href="MainController?btnAction=logout">Logout</a><br>
        <a href="MainController?btnAction=create">Create New Product</a>
        <c:set var = "listType" scope = "page" value = "${sessionScope.LIST_TYPE}"/>
        <form action="MainController">
            <input type="text" name="txtNamePro"/>
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
                    Choose Type Product: <select name="cbbType" >
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
            <input type="submit" name ="btnAction" value="search"/>
        </form>
        <c:set var="listProduct" scope = "page" value="${sessionScope.SEARCH_MANAGE}"/>
        <c:if test="${listProduct==null}">
            <c:set var="listProduct" scope = "page" value="${sessionScope.LIST_MANAGE}"/>
        </c:if>  
        <c:if test="${not empty listProduct}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Type</th>
                        <th>Price</th>
                        <th>Date of create</th>
                        <th>Describe</th>
                        <th>Image</th>
                        <th>Quantity</th>
                        <th>Availability</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" varStatus="counter" items="${listProduct}">
                    <form action="MainController">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                <input type="text" name="txtProductID" value="${product.productID}" readonly="true"/>
                            </td>
                            <td>
                                <input type="text" name="txtProductName" value="${product.productName}"/>
                            </td>
                            <td>
                                <c:if test="${listType!=null}">
                                    <c:set var="typeName" scope="page" value=""/>
                                    <c:forEach var="type" varStatus="counter" items="${listType}">
                                        <c:if test="${type.typeID.equalsIgnoreCase(product.typeID)}">
                                            <c:set var="typeName" scope="page" value="${type.typeName}"/>
                                        </c:if>    
                                    </c:forEach>
                                    <select name="cbbType" >
                                        <option value="${product.typeID}">${typeName}</option>
                                        <c:forEach var="type" varStatus="counter" items="${listType}">
                                            <c:if test="${!type.typeID.equalsIgnoreCase(product.typeID)}">
                                                <option value="${type.typeID}"><c:out value="${type.typeName}"/></option>
                                            </c:if>    
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </td>
                            <td>
                                <input type="text" name="txtPrice" value="${product.price}"/>
                            </td>
                            <td>
                                <input type="text"  value="${product.date}" readonly="true"/>
                            </td>
                            <td>
                                <input type="text" name="txtDescribe" value="${product.describe}"/>
                            </td>
                            <td>
                                <input type="hidden" name="image" value="${product.image}"/>
                                <img src="${product.image}" alt="${product.image}" width="50px" height="50px"/>
                                <input type="file" name="fileImage" accept="image/png, image/jpeg"/> 
                                <input type="reset" value="Reset"/>
                            </td>
                            <td>
                                <input type="number" name="txtQuantity"  value="${product.quantity}" min="0"/>
                            </td>
                            <td>
                                <select name="cbbAvailability" >
                                    <option value="${product.availability}">${product.availability}</option>
                                    <option value="${!product.availability}">${!product.availability}</option>
                                </select>
                            </td>

                            <td>
                                <input type="submit" name="btnAction" value="Update"/>  
                            </td>
                            <td>
                                <c:url var="delete" value="MainController">
                                    <c:param name="btnAction" value="delete"></c:param>
                                    <c:param name="productID" value="${product.productID}"></c:param>  
                                </c:url>
                                <a href="${delete}" onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <form action="PagingController">
        <input type="submit" name="page" value="<"/> <input type="submit" name="page" value=">"/>
    </form>
</body>
</html>
