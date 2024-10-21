package com.assignment.models.repositories;

import java.util.HashMap;
import java.util.Map;

public class QueryBuilder<T> {
    private final StringBuilder query;
    private final Map<String, Object> params;
    private int index;
    private int setIndex;
    private int whereIndex;
    private final Class<T> entityClass;

    public QueryBuilder(
            Class<T> entityClass) {
        this.entityClass = entityClass;
        query = new StringBuilder();
        params = new HashMap<>();
        index = 0;
        setIndex = 0;
        whereIndex = 0;
    }

    // count
    public QueryBuilder<T> count() {
        query.append("select count(*) from ")
                .append(entityClass.getName())
                .append(" "); // đếm số lượng
        return this;
    }

    public QueryBuilder<T> count(String alias) {
        query.append("select count(*) from ")
                .append(entityClass.getName())
                .append(" ")
                .append(alias)
                .append(" "); // đếm số lượng
        return this;
    }

    public QueryBuilder<T> countBy(String field, String alias) {
        query.append("select count(")
                .append(field)
                .append(") from ")
                .append(entityClass.getName())
                .append(" ")
                .append(alias)
                .append(" "); // đếm theo trường
        return this;
    }


    // avg
    public QueryBuilder<T> avg(String field) {
        query.append("select avg(")
                .append(field)
                .append(") from ")
                .append(entityClass.getName())
                .append(" "); // trung bình
        return this;
    }

    public QueryBuilder<T> avg(String field, String alias) {
        query.append("select avg(")
                .append(alias)
                .append(".")
                .append(field)
                .append(") from ")
                .append(entityClass.getName())
                .append(" ")
                .append(alias)
                .append(" "); // trung bình
        return this;
    }


    // select
    public QueryBuilder<T> select(String... fields) {
        query.append("select ");
        for (String field : fields) {
            query.append(field).append(", ");
        }
        query.deleteCharAt(query.length() - 2); // loại bỏ dấu phẩy cuối cùng
        return this;
    }

    public QueryBuilder<T> select() {
        return this; // chọn tất cả
    }


    // update
    public QueryBuilder<T> update() {
        query.append("update ")
                .append(entityClass.getName())
                .append(" "); // cập nhật
        return this;
    }

    // update alias
    public QueryBuilder<T> update(String alias) {
        query.append("update ")
                .append(entityClass.getName())
                .append(" ")
                .append(alias)
                .append(" "); // cập nhật với alias
        return this;
    }

    // set
    public QueryBuilder<T> set(String field, Object value) {
        String paramName = "value" + index++;
        setIndex++;
        if (setIndex == 1) {
            query.append("set ")
                    .append(field)
                    .append(" = :")
                    .append(paramName)
                    .append(" "); // thiết lập
        } else {
            query.append(", ")
                    .append(field)
                    .append(" = :")
                    .append(paramName)
                    .append(" "); // thiết lập
        }
        params.put(paramName, value);
        return this;
    }

    // increase
    public QueryBuilder<T> increase(String field, int value) {
        setIndex++;
        query.append("set ")
                .append(field)
                .append(" = ")
                .append(field)
                .append(" + ")
                .append(value)
                .append(" "); // tăng
        return this;
    }




    public QueryBuilder<T> from() {
        query.append("from ")
                .append(entityClass.getName())
                .append(" "); // tên bảng
        return this;
    }

    public QueryBuilder<T> from(String alias) {
        query.append("from ")
                .append(entityClass.getName())
                .append(" ")
                .append(alias)
                .append(" "); // tên bảng với alias
        return this;
    }

    // fetch
    public QueryBuilder<T> fetch(String associationPath) {
        query.append("left join fetch ")
                .append(associationPath)
                .append(" "); // tên bảng liên kết
        return this;
    }

    public QueryBuilder<T> fetch(String... associationPaths) {
        for (String associationPath : associationPaths) {
            query.append("left join fetch ")
                    .append(associationPath)
                    .append(" "); // tên bảng liên kết
        }
        return this;
    }

    // join
    public QueryBuilder<T> join(String associationPath) {
        query.append("join ")
                .append(associationPath)
                .append(" "); // tên bảng liên kết
        return this;
    }

    public QueryBuilder<T> join(String... associationPaths) {
        for (String associationPath : associationPaths) {
            query.append("join ")
                    .append(associationPath)
                    .append(" "); // tên bảng liên kết
        }
        return this;
    }


    // group by: nhóm theo
    public QueryBuilder<T> groupBy(String field) {
        query.append("group by ")
                .append(field)
                .append(" "); // nhóm theo
        return this;
    }

    // group by: nhóm theo
    public QueryBuilder<T> groupBy(String... fields) {
        query.append("group by ");
        for (String field : fields) {
            query.append(field).append(", ");
        }
        query.deleteCharAt(query.length() - 2); // loại bỏ dấu phẩy cuối cùng
        return this;
    }



    // having: điều kiện having
    public QueryBuilder<T> having(String field, Object value) {
        String paramName = "value" + index++;
        if (index == 1) {
            query.append("having ")
                    .append(field)
                    .append(" = :")
                    .append(paramName)
                    .append(" "); // điều kiện having
        } else {
            query.append("and ")
                    .append(field)
                    .append(" = :")
                    .append(paramName)
                    .append(" "); // điều kiện having
        }
        params.put(paramName, value);
        return this;
    }

    // having and: điều kiện having and
    public QueryBuilder<T> havingAnd(String field, Object value) {
        return having(field, value);
    }

    // having or: điều kiện having or
    public QueryBuilder<T> havingOr(String field, Object value) {
        String paramName = "value" + index++;
        query.append("or ")
                .append(field)
                .append(" = :")
                .append(paramName)
                .append(" "); // điều kiện having or
        params.put(paramName, value);
        return this;
    }

    // having like: điều kiện having like
    public QueryBuilder<T> havingLike(String field, Object value) {
        String paramName = "value" + index++;
        if (index == 1) {
            query.append("having ")
                    .append(field)
                    .append(" like :")
                    .append(paramName)
                    .append(" "); // điều kiện having like
        } else {
            query.append("and ")
                    .append(field)
                    .append(" like :")
                    .append(paramName)
                    .append(" "); // điều kiện having like
        }
        params.put(paramName, value);
        return this;
    }

    // having like and: điều kiện having like and
    public QueryBuilder<T> havingLikeAnd(String field, Object value) {
        return havingLike(field, value);
    }

    // having like or: điều kiện having like or
    public QueryBuilder<T> havingLikeOr(String field, Object value) {
        String paramName = "value" + index++;
        query.append("or ")
                .append(field)
                .append(" like :")
                .append(paramName)
                .append(" "); // điều kiện having like or
        params.put(paramName, value);
        return this;
    }


    // custom having query
    public QueryBuilder<T> havingQuery(String query) {
        this.query.append("having ")
                .append(query)
                .append(" "); // điều kiện having
        return this;
    }


    // where
    public QueryBuilder<T> where(String field, Object value) {
        String paramName = "value" + index++;
        whereIndex++;
        if (whereIndex == 1) {
            query.append("where ")
                    .append(field)
                    .append(" = :")
                    .append(paramName)
                    .append(" "); // điều kiện where
        } else {
            query.append("and ")
                    .append(field)
                    .append(" = :")
                    .append(paramName)
                    .append(" "); // điều kiện where
        }
        params.put(paramName, value);
        return this;
    }

    // whereNull
    public QueryBuilder<T> whereNull(String field) {
        if (whereIndex == 0) {
            query.append("where ")
                    .append(field)
                    .append(" is null "); // điều kiện where null
        } else {
            query.append("and ")
                    .append(field)
                    .append(" is null "); // điều kiện where null
        }
        return this;
    }

    public QueryBuilder<T> and(String field, Object value) {
        return where(field, value);
    }

    public QueryBuilder<T> or(String field, Object value) {
        String paramName = "value" + index++;
        query.append("or ")
                .append(field)
                .append(" = :")
                .append(paramName)
                .append(" "); // điều kiện or
        params.put(paramName, value);
        return this;
    }


    // where like
    public QueryBuilder<T> whereLike(String field, Object value) {
        String paramName = "value" + index++;
        whereIndex++;
        if (whereIndex == 1) {
            query.append("where ")
                    .append(field)
                    .append(" like :")
                    .append(paramName)
                    .append(" "); // điều kiện where like
        } else {
            query.append("and ")
                    .append(field)
                    .append(" like :")
                    .append(paramName)
                    .append(" "); // điều kiện where like
        }
        params.put(paramName, value);
        return this;
    }

    public QueryBuilder<T> andLike(String field, Object value) {
        return whereLike(field, value);
    }

    public QueryBuilder<T> orLike(String field, Object value) {
        String paramName = "value" + index++;
        query.append("or ")
                .append(field)
                .append(" like :")
                .append(paramName)
                .append(" "); // điều kiện or like
        params.put(paramName, value);
        return this;
    }




    // order by: sắp xếp
    public QueryBuilder<T> orderBy(String field, boolean asc) {
        query.append("order by ")
                .append(field)
                .append(" ")
                .append(asc ? "asc" : "desc")
                .append(" "); // sắp xếp
        return this;
    }

    // order next: sắp xếp tiếp theo
    public QueryBuilder<T> orderNext(String field, boolean asc) {
        query.append(", ")
                .append(field)
                .append(" ")
                .append(asc ? "asc" : "desc")
                .append(" "); // sắp xếp tiếp theo
        return this;
    }

    // limit: giới hạn số lượng
    public QueryBuilder<T> limit(int limit) {
        query.append("limit ")
                .append(limit)
                .append(" "); // giới hạn số lượng
        return this;
    }

    public String build() {
        return query.toString();
    }

    // reset 
    public QueryBuilder<T> reset() {
        query.setLength(0);
        params.clear();
        index = 0;
        setIndex = 0;
        whereIndex = 0;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

}
