package com.assignment.models.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/*
 * 
 * Hàm trừu tượng Repositories chứa các phương thức CRUD cơ bản
 * @param <T> : kiểu dữ liệu của đối tượng
 * @param <ID> : kiểu dữ liệu của khóa chính
 */
@Repository
@Transactional
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
        return getCurrentSession().createQuery("from " + getEntityClass().getName() + " where " + field + " = :value", getEntityClass())
                .setParameter("value", value).uniqueResult();
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
}
