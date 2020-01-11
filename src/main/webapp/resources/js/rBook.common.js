var context, form;

function makeEditable(ctx) {
    context = ctx;
    context.datatableApi = $("#datatable").DataTable(
        // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, ctx.datatableOpts,
            {
                "ajax": {
                    "url": context.ajaxUrl,
                    "dataSrc": ""
                },
                "paging": false,
                "info": true,
                "language": {
                    "search": i18n["common.search"]
                }
            }
        ));

    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    $.ajaxSetup({cache: false});
}

function add() {
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    form.find("tr[id=tempData]").remove();
    $("#editRow").modal();
}

function updateRow(id) {
    $("#modalTitle").html(i18n["editTitle"]);
    form.find("tr[id=tempData]").remove();
    $.get(context.ajaxUrl + "withprod/" + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
            if (key==="productList"){
                for (var i=0;i<value.length;i++){
                    jQuery('.plus').trigger("click");
                    form.find('tr[id=tempData]').last().find("input[name=name]").val(value[i].name);
                    form.find('tr[id=tempData]').last().find("input[name=volume]").val(value[i].volume);
                    form.find('tr[id=tempData]').last().find("input[name=volumeMeasure]").val(value[i].volumeMeasure);
                }
            }
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: context.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            context.updateTable();
            successNoty("common.deleted");
        });
    }
}

function updateTableByData(data) {
    context.datatableApi.clear().rows.add(data).draw();
}

function save() {
    serializeRecipeFormData(form);
    closeNoty();
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: JSON.stringify(serializeRecipeFormData(form)),
        dataType: "json",
        contentType: "application/json"
    }).done(function (result) {
        $("#editRow").modal("hide");
        context.updateTable();
        successNoty("common.saved");
    });
}

var failedNote;

function serializeRecipeFormData(form) {
    var ajaxData={};
    ajaxData.id = $('input[name=id]').val();
    ajaxData.description = $('input[name=description]').val();
    ajaxData.servings = $('input[name=servings]').val();
    ajaxData.productList = serializeProducts(form);
    return ajaxData;
}

function serializeProducts(form) {
    var productArray = form.find('input[id=productData]').serializeArray();
    var resultArray = [];
    var currentObject = {};
    for (let i = 0; i<productArray.length; i++){
        if (productArray[i]['name']==="name"){
            currentObject={};
            currentObject[productArray[i]['name']]=productArray[i]['value'];
        } else /*if (productArray[i]['name']==="description" || productArray[i]['name']==="volumeMeasure" || productArray[i]['name']==="volume") */{
            currentObject[productArray[i]['name']]=productArray[i]['value'];
            if (productArray[i]['name']==="volumeMeasure") resultArray.push(currentObject);
        }
    }
    return  resultArray;
}

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    var errorInfo = JSON.parse(jqXHR.responseText);
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    }).show();
}

function viewRow(id) {
    $("#viewTitle").html(i18n["viewTitle"]);
    $('li#recipeData').remove();
    $.get(context.ajaxUrl+ "withprod/" + id, function (data) {
        $.each(data, function (key, value) {
            if (key==="productList"){
                for (var i=0; i< value.length;i++){
                    var list = $('ul#liProducts');
                    var li = $('<li id="recipeData"/>').appendTo(list);
                    li.text(value[i].name+' '+value[i].volume+value[i].volumeMeasure)
                }
            } else {
                var list = $('ul#liView');
                var li = $('<li id="recipeData"/>').appendTo(list);
                li.text(value);
            }
        });
        $('#viewRow').modal();
    });
}

function renderViewBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='viewRow(" + row.id + ");'><span class='fa fa-file-text'></span></a>";
    }
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}