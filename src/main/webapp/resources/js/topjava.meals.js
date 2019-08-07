// $(document).ready(function () {
var url = "ajax/meals/";

$(function () {
    makeEditable({
            ajaxUrl: url,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );
});

function filter() {
    var filterForm = $('#filterForm');
    $.ajax({
        type: "GET",
        url: url + "filter",
    data: filterForm.serialize()
    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered")
    })
}

function cancelFilter() {
    if(confirm("Are you sure?")) {
        $("#filterForm")[0].reset();
        $.get(context.ajaxUrl, function (data) {
            context.datatableApi.clear().rows.add(data).draw();
        });
    }
}

function updateTable() {
    filter();
}