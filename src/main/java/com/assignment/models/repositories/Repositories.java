package com.assignment.models.repositories;

import java.util.List;

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
        return getCurrentSession()
                .createQuery("from " + getEntityClass().getName() + " where " + field + " = :value", getEntityClass())
                .setParameter("value", value).uniqueResult();
    }

    public T findUniqueBy(Object value, String... fields) {
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

    public List<T> getResultList(
            QueryBuilder<T> queryBuilder) {
        return getCurrentSession().createQuery(queryBuilder.build(), getEntityClass())
                .setProperties(queryBuilder.getParams()).list();
    }

    // lấy theo trang
    public List<T> getResultList(int page, int limit, QueryBuilder<T> queryBuilder) {
        return getCurrentSession().createQuery(queryBuilder.build(), getEntityClass())
                .setProperties(queryBuilder.getParams()).setFirstResult((page - 1) * limit).setMaxResults(limit).list();
    }

    public T getSingleResult(QueryBuilder<T> queryBuilder) {
        return (T) getCurrentSession().createQuery(queryBuilder.build(), getEntityClass())
                .setProperties(queryBuilder.getParams()).uniqueResult();
    }

    public Long count(QueryBuilder<T> queryBuilder) {
        Long result =  getCurrentSession()
                .createQuery(queryBuilder.build(), Long.class)
                .setProperties(queryBuilder.getParams())
                .uniqueResult();
        return result == null ? 0 : result;
    }

    public Double avg(QueryBuilder<T> queryBuilder) {
        Double result = getCurrentSession().createQuery(queryBuilder.build(), Double.class)
                .setProperties(queryBuilder.getParams()).uniqueResult();
        return result == null ? 0 : result;
    }

    public void executeUpdate(QueryBuilder<T> queryBuilder) {
        getCurrentSession().createMutationQuery(queryBuilder.build())
                .setProperties(queryBuilder.getParams()).executeUpdate();
    }



    // R - Read: lấy thông tin tất cả đối tượng
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + getEntityClass().getName(), getEntityClass()).list();
    }

    public List<T> findAllBy(String field, Object value) {
        return getCurrentSession()
                .createQuery("from " + getEntityClass().getName() + " where " + field + " = :value", getEntityClass())
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

}
