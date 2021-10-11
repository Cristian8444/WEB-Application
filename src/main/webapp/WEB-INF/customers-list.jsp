<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>Customers</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
  </head>

  <body>
  	<div class="page-header">
  		<div class="pull-left">
  			Welcome ${logged_user}! This is a list of all customers.
  			<a href="/logout" class="btn btn-primary">Logout</a>
  			<div><a href="/customers/add" class="btn btn-primary">Add New Customer!</a><div>
  		</div>
  		<div class="clearfix"></div>
  	</div>



        <table class="table table-striped">
            <tr>
                <th>Name</th>
                <th>Phone</th>
                 <th>Email</th>
                <th>Actions</th>

            </tr>
            <c:forEach items="${customers}" var="customer">
                <tr>
                    <td><c:out value="${customer.name}" /></td>
                    <td><c:out value="${customer.phone}" /></td>
                    <td><c:out value="${customer.email}" /></td>
                    <td>
                        <a href="<c:url value="/customers/${customer.id}/edit"/> " class="btn btn-info">Edit</a>
                        <a href="<c:url value="/customers/${customer.id}/orders"/> " class="btn btn-info">View Orders</a>
                        <a href="<c:url value="/customers/${customer.id}/delete"/> " class="btn btn-danger">Delete Customer</a>
                    </td>
                </tr>
            </c:forEach>
    </table>
  </body>
</html>
