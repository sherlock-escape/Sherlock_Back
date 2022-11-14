package com.example.sherlockescape.repository;

import com.example.sherlockescape.domain.QTheme;
import com.example.sherlockescape.domain.Theme;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.sherlockescape.domain.QTheme.theme;


@RequiredArgsConstructor
@Repository
public class ThemeRepositoryImpl implements ThemeQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Theme> findFilter(Pageable pageable, List<String> location, List<String> genre, List<Integer> themeScore, List<Integer> difficulty, List<Integer> people) {

        QTheme theme = QTheme.theme;

        QueryResults<Theme> result = queryFactory
                .from(theme)
                .select(theme)
                .where(
                        eqLocation(location),
                        eqGenres(genre),
                        eqThemeScore(themeScore),
                        eqDifficulty(difficulty),
                        eqPeople(people)
                )
                .limit(pageable.getPageSize()) // 현재 제한한 갯수
                .offset(pageable.getOffset())
                .orderBy(theme.id.desc())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());

    }

    private BooleanExpression eqLocation(List<String> location) {
        return location != null ? Expressions.anyOf(location.stream().map(this::isFilteredLoacation).toArray(BooleanExpression[]::new)) : null;
    }

    private BooleanExpression isFilteredLoacation(String location) {
        return theme.company.location.contains(location);
    }


    private BooleanExpression eqGenres(List<String> genre) {
        return genre != null ? Expressions.anyOf(genre.stream().map(this::isFilteredGenre).toArray(BooleanExpression[]::new)) : null;
    }

    private BooleanExpression isFilteredGenre(String genre) {
        return theme.genre.contains(genre);
    }


    private BooleanExpression eqThemeScore(List<Integer> themeScore) {
        if (themeScore == null) {
            return null;
        } else {
            Integer minScore = Collections.min(themeScore);
            Integer maxScore = Collections.max(themeScore);
            return theme.themeScore.between(minScore, maxScore + 1);
        }
    }


    private BooleanExpression eqDifficulty(List<Integer> difficulty) {
        if (difficulty == null) {
            return null;
        } else {
            Integer minDifficulty = Collections.min(difficulty);
            Integer maxDifficulty = Collections.max(difficulty);
            return theme.difficulty.between(minDifficulty, maxDifficulty + 1);
        }
    }

    private BooleanExpression eqPeople(List<Integer> people) {
        return people != null ? Expressions.anyOf(people.stream().map(this::isFilteredPeople).toArray(BooleanExpression[]::new)) : null;
    }

    private BooleanExpression isFilteredPeople(Integer people) {
        return theme.minPeople.loe(people).and(theme.maxPeople.goe(people));
    }
}


