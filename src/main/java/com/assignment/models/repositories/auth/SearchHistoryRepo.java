package com.assignment.models.repositories.auth;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.auth.SearchHistory;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.auth.SearchScope;
import com.assignment.models.repositories.QueryBuilder;

@Repository
@Transactional
public class SearchHistoryRepo extends Repositories<SearchHistory, Long>{

    @Override
    protected Class<SearchHistory> getEntityClass() {
        return SearchHistory.class;
    }

    public List<SearchHistory> findAllByUserIdAndScope(Long userId, SearchScope scope) {
        QueryBuilder<SearchHistory> customQuery = new QueryBuilder<>(SearchHistory.class);
        customQuery.from("sh")
                    .where("sh.user.id", userId)
                    .where("sh.scope", scope);
        return getResultList(customQuery);
    }

}
