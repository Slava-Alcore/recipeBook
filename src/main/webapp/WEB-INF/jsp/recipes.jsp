<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/rBook.common.js" defer></script>
<script type="text/javascript" src="resources/js/rBook.recipes.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="recipe.title"/></h3>
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="offset-1 col-2">
                            <label for="startDate"><spring:message code="recipe.startDate"/></label>
                            <input class="form-control" name="startDate" id="startDate" autocomplete="off">
                        </div>
                        <div class="col-2">
                            <label for="endDate"><spring:message code="recipe.endDate"/></label>
                            <input class="form-control" name="endDate" id="endDate" autocomplete="off">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-danger" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="updateFilteredTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="recipe.filter"/>
                </button>
            </div>
        </div>
        <br/>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="recipe.date"/></th>
                <th><spring:message code="recipe.description"/></th>
                <th><spring:message code="recipe.servings"/></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dynamicTable" class="col-form-label"><spring:message
                                code="recipe.productList"/></label>
                        <%--<input type="text" class="form-control" id="productList" name="productList"
                               placeholder="<spring:message code="recipe.description"/>">--%>
                        <table class="table information_json" id="dynamicTable">
                            <tr>
                                <th><spring:message code="recipe.product.name"/></th>
                                <th><spring:message code="recipe.product.volume"/></th>
                                <th><spring:message code="recipe.product.volumeMeasure"/></th>
                                <th></th>
                            </tr>
                            <tr class="information_json_plus">
                                <td></td>
                                <td></td>
                                <td></td>
                                <td><span class="btn btn-success plus pull-right">+</span></td>
                            </tr>
                        </table>
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="recipe.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="recipe.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="servings" class="col-form-label"><spring:message code="recipe.servings"/></label>
                        <input type="number" class="form-control" id="servings" name="servings" placeholder="1000">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" tabindex="-2" id="viewRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="viewTitle"></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <table>
                    <tr>
                        <td style="width:30%" class="col-2">
                            <ul id="liProducts">
                            </ul>
                        </td>
                        <td style="width:70%" class="col-3">
                            <ul id="liView">
                            </ul>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.close"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="recipe"/>
</jsp:include>
<script>
    jQuery('.plus').click(function(){
        jQuery('.information_json_plus').before(
            '<tr id="tempData">' +
            '<td><input type="text" class="form-control" id="productData" name="name" placeholder="<spring:message code="recipe.product.name"/>"></td>' +
            '<td><input type="number" class="form-control" id="productData" name="volume" placeholder="<spring:message code="recipe.product.volume"/>"></td>' +
            '<td><input type="text" class="form-control" id="productData" name="volumeMeasure" placeholder="<spring:message code="recipe.product.volumeMeasure"/>"></td>' +
            '<td><span class="btn btn-danger minus pull-right">&ndash;</span></td>' +
            '</tr>'
        );
    });
    // on - так как элемент динамически создан и обычный обработчик с ним не работает
    jQuery(document).on('click', '.minus', function(){
        jQuery( this ).closest( 'tr' ).remove(); // удаление строки с полями
    });
</script>
</html>
