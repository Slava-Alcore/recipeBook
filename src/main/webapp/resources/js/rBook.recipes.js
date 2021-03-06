var recipesAjaxUrl = "ajax/profile/recipes/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: recipesAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(recipesAjaxUrl, updateTableByData);
}

// http://api.jquery.com/jQuery.ajax/#using-converters
$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            var json = JSON.parse(stringData);
            $(json).each(function () {
                this.date = this.date.replace('T', ' ').substr(0, 10);
            });
            return json;
        }
    }
});

$(function () {
    makeEditable({
        ajaxUrl: recipesAjaxUrl,
        datatableOpts: {
            "columns": [
                {
                    "data": "date"
                },
                {
                    "data": "description"
                },
                {
                    "data": "servings"
                },
                {
                    "render": renderViewBtn,
                    "defaultContent": "",
                    "orderable": false
                },
                {
                    "render": renderEditBtn,
                    "defaultContent": "",
                    "orderable": false
                },
                {
                    "render": renderDeleteBtn,
                    "defaultContent": "",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
        },
        updateTable: updateFilteredTable
    });

    $.datetimepicker.setLocale(localeCode);

//  http://xdsoft.net/jqplugins/datetimepicker/
    var startDate = $('#startDate');
    var endDate = $('#endDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                maxDate: endDate.val() ? endDate.val() : false
            })
        }
    });
    endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                minDate: startDate.val() ? startDate.val() : false
            })
        }
    });

    /*$('#date').datetimepicker({
        format: 'Y-m-d'
    });*/
});