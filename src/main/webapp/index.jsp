<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<head>
    <title>TODO List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="js/jsLoader.js"></script>
</head>
<body>
<div class="nav-list">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/index.jsp">Главная</a>
        </li>
        <c:if test="${user == null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/register.jsp">Регистрация</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/auth.jsp">Войти</a>
            </li>
        </c:if>
        <c:if test="${user != null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">
                    <c:out value="${user.name}"/> | Выйти</a>
                </a>
            </li>
        </c:if>
    </ul>
</div>
<div class="container-fluid">
    <h5>Добавить новое задание: </h5>
    <form action="index.do" method="post">
        <div class="form-row align-items-center">
            <div class="col-auto">
                <div class="form-group">
                    <textarea class="form-control" id="Text" name="Text"
                              placeholder="Описание" rows="3" cols="150"
                              title="Поле Описание не может быть пустым"></textarea>
                </div>
                <button type="submit" class="btn btn-primary mb-2"
                        onclick="return validate();">Добавить
                </button>
            </div>
        </div>
    </form>
    <br>
    <input type="checkbox" class="hider" id="hider">
    <label for="hider">Показать список полностью</label>
    <form action="index.do" method="get">
        <table class="table">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">#</th>
                <th scope="col">Описание</th>
                <th scope="col">Дата</th>
                <th scope="col">Статус</th>
                <th scope="col">Автор</th>
            </tr>
            </thead>
            <tbody id="tb">
            </tbody>
        </table>
    </form>
</div>
</body>
</html>