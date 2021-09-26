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
        url: 'http://localhost:8080/todo/index.do',
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
                    + "<td>" + item.user.name + "</td>"
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
        url: 'http://localhost:8080/todo/index.do',
        dataType: 'text',
        data: {id: id},
        success: function (data) {
            window.location.reload();
        }
    })
}