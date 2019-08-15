$.datetimepicker.setLocale('ru');
$(function () {
    $("#startDate").datetimepicker({
        format: 'Y-m-d',
        timepicker: false,
        onShow: function () {
            this.setOptions({
                maxDate: minMaxSolver($("#endDate"))
            })
        }
    });

    $("#endDate").datetimepicker({
        format: 'Y-m-d',
        timepicker: false,
        onShow: function () {
            this.setOptions({
                minDate: minMaxSolver($("#startDate"))
            })
        }
    });

    $("#startTime").datetimepicker({
        format: 'H:i',
        datepicker: false,
        onShow: function () {
            this.setOptions({
                maxTime: minMaxSolver($("#endTime"))
            })

        }
    });

    $("#endTime").datetimepicker({
        format: 'H:i',
        datepicker: false,
        onShow: function () {
            this.setOptions({
                minTime: minMaxSolver($("#startTime"))
            })
        }
    });

    $.ajaxSetup({
        converters: {
            "text json": function (input) {
                var output = JSON.parse(input);
                $(output).each(function (index, item) {
                    item.dateTime = item.dateTime.substring(0, 10) + " " + item.dateTime.substring(11, 16);
                });
                return output
            }
        }
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
                    "data": "dateTime"
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

function minMaxSolver(limit) {
    return limit.val() ? limit.val() : false
}