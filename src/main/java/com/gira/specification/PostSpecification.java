package com.gira.specification;

import com.gira.entity.Post;
import com.gira.form.PostFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> buildSpec(PostFilterForm form){
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String search = form.getSearch();
            if (StringUtils.hasText(search)){
                String pattern = "%" + search.trim() + "%";
                Predicate hasTitleLike = builder.like(root.get("title"), pattern);
                predicates.add(hasTitleLike);
            }
            var minCreateDate = form.getMinCreatedDate();
            if (minCreateDate != null){
                var minCreateAt = LocalDateTime.of(minCreateDate, LocalTime.MIN);
                var predicate = builder.greaterThanOrEqualTo(
                        root.get("createdAt"), minCreateAt
                );
                predicates.add(predicate);
            }

            var maxCreateDate = form.getMaxCreatedDate();
            if (maxCreateDate != null){
                var maxCreatedAt = LocalDateTime.of(maxCreateDate, LocalTime.MAX);
                var predicate = builder.lessThanOrEqualTo(
                        root.get("createdAt"), maxCreatedAt
                );
                predicates.add(predicate);
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
