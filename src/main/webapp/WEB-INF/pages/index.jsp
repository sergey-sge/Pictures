<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pictures</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <h3><img height="50" width="55" src="<c:url value="/static/logo.png"/>"/><a href="/">Picture List</a></h3>

    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul id="groupList" class="nav navbar-nav">
                    <li><button type="button" id="delete_picture" class="btn btn-default navbar-btn">Delete Picture</button></li>
                    <li><button type="button" id="archivate_picture" class="btn btn-default navbar-btn">Archivate Pictures</button></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <table class="table table-striped">
        <thead>
        <tr>
            <td></td>
            <td><b>Id</b></td>
            <td><b>Name</b></td>
            <td><b>Picture</b></td>
        </tr>
        </thead>
        <c:forEach items="${pictures}" var="picture">
            <tr>
                <td><input type="checkbox" name="toDelete[]" value="${picture.id}" id="checkbox_${picture.id}"/></td>
                <td>${picture.id}</td>
                <td>${picture.name}</td>
                <td><img width="100" height="75" src="/static/${picture.name}.jpeg/"></td>
            </tr>
        </c:forEach>
    </table>

    <nav aria-label="Page navigation">
        <ul class="pagination">
            <c:if test="${allPages ne null}">
                <c:forEach var="i" begin="1" end="${allPages}">
                    <li><a href="/?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
                </c:forEach>
            </c:if>
        </ul>
    </nav>
</div>

<script>
    $('.dropdown-toggle').dropdown();

    $('#delete_picture').click(function(){
        var data = { 'toDelete[]' : []};
        $(":checked").each(function() {
            data['toDelete[]'].push($(this).val());
        });
        $.post("/pictures/delete", data, function(data, status) {
            window.location.reload();
        });
    });
    $('#archivate_picture').click(function(){
        var data = { 'toArchive[]' : []};
        $(":checked").each(function() {
            data['toArchive[]'].push($(this).val());
        });
        $.post("/pictures/archivate", data, function(data, status) {
            window.location.reload();
        });
    });
</script>
</body>
</html>