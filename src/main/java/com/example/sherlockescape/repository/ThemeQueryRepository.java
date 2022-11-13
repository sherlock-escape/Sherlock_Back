package com.example.sherlockescape.repository;

import com.example.sherlockescape.domain.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThemeQueryRepository {
    Page<Theme> findFilter(Pageable pageable, List<String> location, List<String> genre/*, List<Double> themeScore, List<Double> difficulty, List<int> minPeople, List<int> maxPeople*/) ;
}