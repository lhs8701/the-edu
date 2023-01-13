package joeuncamp.dabombackend.global.constant;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public enum CategoryGroup {
    PROGRAMING("프로그래밍", Arrays.asList(CategoryType.BACK_END, CategoryType.FRONT_END)),
    FOREIGN_LANGUAGE("외국어", Arrays.asList(CategoryType.TOEIC, CategoryType.TOEFL)),
    LICENSE("자격증", Arrays.asList(CategoryType.DATA_PROCESSING, CategoryType.ELECTRICAL)),
    MONEY_MANAGEMENT("재테크", Arrays.asList(CategoryType.FUND, CategoryType.STOCK)),
    EMPTY("없음", Collections.emptyList());

    private final String title;
    private final List<CategoryType> categoryList;

    public static CategoryGroup findByCategoryType(CategoryType key){
        return Arrays.stream(values())
                .filter(group -> group.hasCategory(key))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasCategory(CategoryType key){
        return categoryList.stream()
                .anyMatch(category -> category == key);
    }
}
