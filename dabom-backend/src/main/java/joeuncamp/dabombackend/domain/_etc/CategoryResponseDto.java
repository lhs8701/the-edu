package joeuncamp.dabombackend.domain._etc;

import joeuncamp.dabombackend.global.constant.CategoryGroup;
import joeuncamp.dabombackend.global.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CategoryResponseDto {
    String groupName;
    List<String> categoryList;
    public CategoryResponseDto(CategoryGroup categoryGroup){
        this.groupName = categoryGroup.getTitle();
        this.categoryList = categoryGroup.getCategoryList().stream().map(CategoryType::getTitle).collect(Collectors.toList());
    }
}
