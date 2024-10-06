package com.assignment.models.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/*
 * 
 * Hàm trừu tượng Repositories chứa các phương thức CRUD cơ bản
 * @param <T> : kiểu dữ liệu của đối tượng
 * @param <ID> : kiểu dữ liệu của khóa chính
 */
@Repository
public abstract class Repositories<T, ID> {

    private final StringBuilder defaultQuery = new StringBuilder();
    private Map<String, Object> params = new HashMap<>();
    private int index = 0;

    // Sử dụng sessionFactory để tạo ra các phiên làm việc với cơ sở dữ liệu
    @Autowired
    private SessionFactory sessionFactory;

    // Phương thức trả về session hiện tại
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    // C - Create: thêm mới một đối tượng
    public void save(T entity) {
        getCurrentSession().persist(entity);
    }

    // R - Read: lấy thông tin một đối tượng
    public T findById(ID id) {
        return (T) getCurrentSession().get(getEntityClass(), id);
    }

    public T findUniqueBy(String field, Object value) {
        if (field == null) {
            return null;
        }
        return getCurrentSession().createQuery("from " + getEntityClass().getName() + " where " + field + " = :value", getEntityClass())
                .setParameter("value", value).uniqueResult();
    }

    public T findUniqueBy(Object value, String ...fields) {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("At least one field must be provided");
        }

        // Tạo truy vấn HQL
        StringBuilder query = new StringBuilder("from " + getEntityClass().getName() + " where ");

        // Xây dựng điều kiện `or` cho từng trường
        for (int i = 0; i < fields.length; i++) {
            query.append(fields[i]).append(" = :value");
            if (i < fields.length - 1) {
                query.append(" or ");
            }
        }

        // Tạo truy vấn và thiết lập tham số
        return getCurrentSession().createQuery(query.toString(), getEntityClass())
                .setParameter("value", value).uniqueResult();
    }



    public Repositories<T, ID> CustomQuery() {
        defaultQuery.setLength(0);
        params.clear();
        index = 0;
        defaultQuery.append("from ").append(getEntityClass().getName());
        return this;
    }
    

    public Repositories<T, ID> CustomQuery(String alias) {
        defaultQuery.setLength(0);
        params.clear();
        index = 0;
        defaultQuery.append("from ").append(getEntityClass().getName()).append(" ").append(alias);
        return this;
    }


    // Fetch dữ liệu từ các bảng liên kết
    public Repositories<T, ID> fetch(String associationPath) {
        if (defaultQuery.length() == 0) {
            throw new IllegalStateException("CustomQuery() must be called before fetch()");
        }
        defaultQuery.append(" left join fetch ").append(associationPath);
        return this;
    }

    public Repositories<T, ID> fetch(String... associationPaths) {
        if (defaultQuery.length() == 0) {
            throw new IllegalStateException("CustomQuery() must be called before fetch()");
        }
        for (String associationPath : associationPaths) {
            defaultQuery.append(" left join fetch ").append(associationPath);
        }
        return this;
    }
    
    
    public Repositories<T, ID> where(String field, Object value) {
        String paramName = "value" + index++;
        if (index == 1) {
            defaultQuery.append(" where ").append(field).append(" = :").append(paramName);
        } else {
            defaultQuery.append(" and ").append(field).append(" = :").append(paramName);
        }
        params.put(paramName, value);
        return this;
    }
    
    public Repositories<T, ID> and(String field, Object value) {
        return where(field, value);
    }
    
    public Repositories<T, ID> or(String field, Object value) {
        String paramName = "value" + index++;
        defaultQuery.append(" or ").append(field).append(" = :").append(paramName);
        params.put(paramName, value);
        return this;
    }
    

    public List<T> getResultList() {
        return getCurrentSession().createQuery(defaultQuery.toString(), getEntityClass())
                .setProperties(this.params).list();
    }

    public T getSingleResult() {
        return (T) getCurrentSession().createQuery(defaultQuery.toString(), getEntityClass())
                .setProperties(this.params).uniqueResult();
    }
    

    // R - Read: lấy thông tin tất cả đối tượng
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + getEntityClass().getName(), getEntityClass()).list();
    }

    public List<T> findAllBy(String field, Object value) {
        return getCurrentSession().createQuery("from " + getEntityClass().getName() + " where " + field + " = :value", getEntityClass())
                .setParameter("value", value).list();
    }


    // U - Update: cập nhật một đối tượng
    public void update(T entity) {
        getCurrentSession().merge(entity);
    }

    // D - Delete: xóa một đối tượng
    public void delete(T entity) {
        getCurrentSession().remove(entity);
    }

    // D - Delete: xóa một đối tượng theo id
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
    
    // Phương thức trừu tượng trả về kiểu dữ liệu của đối tượng
    protected abstract Class<T> getEntityClass();

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
