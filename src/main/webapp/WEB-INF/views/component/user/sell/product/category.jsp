<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal fade modal-fullscreen modal-xl" id="categoryModal" tabindex="-1" aria-labelledby="categoryModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="categoryModalLabel">Chọn danh mục</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="form-control p-3 mb-3">
                    <label for="mainCategory" class="form-label">Danh mục</label>
                    <select class="form-select" id="mainCategory" name="mainCategory">
                        <option value="">Chọn danh mục</option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-control p-3 mb-3">
                    <label for="subCategory" class="form-label">Danh mục con</label>
                    <select class="form-select" id="subCategory" name="subCategory">
                        <option value="">---</option>
                    </select>
                </div>
                
                <div class="form-control p-3 mb-3">
                    <label for="subCategory" class="form-label">Danh mục đầy đủ</label>
                    <input type="text" class="form-control" id="fullCategory" name="fullCategory" readonly/>
                </div>

                <script>
                    document.getElementById('mainCategory').addEventListener('change', function() {
                        const mainCategory = this.value;
                        if(!mainCategory) {
                            document.getElementById('subCategory').innerHTML = '<option value="">---</option>';
                            document.getElementById('fullCategory').value = '';
                            return;
                        }
                        const url = baseHref + "api/category/parent/";

                        fetch(url + mainCategory)
                        .then(response => response.json())
                        .then(data => {
                            const subCategory = document.getElementById('subCategory');
                            subCategory.innerHTML = '<option value="">Chọn danh mục con</option>';
                            data.forEach(category => {
                                const option = document.createElement('option');
                                option.value = category.id;
                                option.textContent = category.name;
                                subCategory.appendChild(option);
                            });
                        });
                        updateFullCategory();
                    });

                    document.getElementById('subCategory').addEventListener('change', function() {
                        updateFullCategory();
                    });

                    function updateFullCategory() {
                        const mainCategory = document.getElementById('mainCategory').selectedOptions[0].textContent;
                        const subCategory = document.getElementById('subCategory').selectedOptions[0].textContent;
                        document.getElementById('fullCategory').value = mainCategory + ' > ' + subCategory;
                    }
                </script>
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                <button type="button" id="catModelOK" data-bs-dismiss="modal" class="btn btn-primary">Chọn</button>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById('catModelOK').addEventListener('click', function() {
        const fullCategory = document.getElementById('fullCategory').value;
        document.getElementById('catShowModel').value = fullCategory;
        const subCategoryID = document.getElementById('subCategory').value;
        document.getElementById('category').value = subCategoryID;
    });
</script>