<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>TODO List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script>
        function validate() {
            let x = Boolean(true);
            if ($('#Text').val() === "") {
                alert($('#Text').attr('title'));
                x = false;
            }
            return x;
        }

        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/todo/index',
                dataType: 'json',
                success: function (data) {
                    let items = "";
                    for (let item of data) {
                        let id = item.id;
                        let fixCheckBox = "";
                        let trClass = "vis";
                        let status = "В процессе";
                        if (item.done === true) {
                            fixCheckBox = "checked disabled"
                            trClass = "inv";
                            status = "Завершено";
                        }
                        items += "<tr class=" + trClass + ">"
                            + "<td><input type=checkbox class=taskCheck name=taskCheck value="
                            + id + " onchange=cl(" + id + ")"
                            + fixCheckBox + "></td>"
                            + "<td>" + id + "</td>"
                            + "<td>" + item.description + "</td>"
                            + "<td>" + item.created + "</td>"
                            + "<td>" + status + "</td>"
                            + "</tr>";
                    }
                    $('#tb').html(items);
                    $(".inv").toggle();
                }
            })
        })

        $(document).ready(function () {
            $(".hider").on('change', function () {
                $(".inv").toggle();
            })
        });

        function cl(id) {
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/todo/index',
                dataType: 'text',
                data: {id: id},
                success: function (data) {
                    window.location.reload();
                }
            })
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <h5>Добавить новое задание: </h5>
    <form action="<%=request.getContextPath()%>/index" method="post">
        <div class="form-row align-items-center">
            <div class="col-auto">
                <div class="form-group">
                    <label for="Text">Описание</label>
                    <textarea class="form-control" id="Text" name="Text" rows="3" cols="52"
                              title="Поле Описание не может быть пустым"></textarea>
                </div>
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-2"
                        onclick="return validate()">Добавить
                </button>
            </div>
        </div>
    </form>
    <br>
    <input type="checkbox" class="hider" id="hider">
    <label for="hider">Показать список полностью</label>

    <table class="table">
        <thead>
        <tr>
            <th scope="col"></th>
            <th scope="col">#</th>
            <th scope="col">Описание</th>
            <th scope="col">Дата</th>
            <th scope="col">Статус</th>
        </tr>
        </thead>
        <form action="index.jsp" method="get">
            <tr>
                <tbody id="tb">
                </tbody>
            </tr>
        </form>
    </table>
</div>
</body>
</html>