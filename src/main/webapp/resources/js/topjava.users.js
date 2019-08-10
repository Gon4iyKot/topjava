// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ]
            })
        }
    );
});

function changeActivity(id, box) {
    var change = box.prop("checked");
    $.ajax({
        type: "POST",
        url: context.ajaxUrl + id,
        data: "changeActivity=" + change
    }).done(function () {
        box.closest("tr").attr("data-activeUser", change);
        successNoty("Activity changed");
    });
}

function updateTable() {
    $.get(context.ajaxUrl, updateTableCommon(data));
}