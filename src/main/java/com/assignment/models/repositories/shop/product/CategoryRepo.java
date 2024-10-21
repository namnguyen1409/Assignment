package com.assignment.models.repositories.shop.product;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.Category;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class CategoryRepo extends Repositories<Category, Long> {

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    public List<Category> findAllByParentId(Long parentId) {
        QueryBuilder<Category> customQuery = new QueryBuilder<>(Category.class);
        customQuery.from("c")
                .where("c.parent.id", parentId);
        return getResultList(customQuery);
    }

    public List<Category> findAllParenCategory() {
        QueryBuilder<Category> customQuery = new QueryBuilder<>(Category.class);
        customQuery.from("c")
                .whereNull("c.parent");
        System.out.println("Generated Query: " + customQuery.build());
        return getResultList(customQuery);
    }

}
