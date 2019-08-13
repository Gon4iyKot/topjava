$.datetimepicker.setLocale('ru');
$(function () {
    $("#startDate").datetimepicker({
        format: 'Y-m-d',
        timepicker: false,
        onShow: function () {
            this.setOptions({
                maxDate: $("#endDate").val() ? $("#endDate").val() : false
            })
        }
    });

    $("#endDate").datetimepicker({
        format: 'Y-m-d',
        timepicker: false,
        onShow: function () {
            this.setOptions({
                minDate: $("#startDate").val() ? $("#startDate").val() : false
            })
        }

    });


    $("#startTime, #endTime").datetimepicker({
        format: 'H:i',
        datepicker: false
    });

    $("#dateTime").datetimepicker({
        format: 'Y-m-d H:i'
    })
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": "ajax/profile/meals/",
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return data.substring(0, 10) + " " + data.substring(11, 16);
                        }
                        return data;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });
});