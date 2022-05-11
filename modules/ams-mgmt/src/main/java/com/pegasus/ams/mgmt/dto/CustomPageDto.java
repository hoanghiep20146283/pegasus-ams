package com.pegasus.ams.mgmt.dto;

import org.springframework.data.domain.Page;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomPageDto<T> {
    List<T> content;
    CustomPageable pageable;

    public CustomPageDto(Page<T> page, String sortBy) {
        this.content = page.getContent();
        this.pageable = new CustomPageable(sortBy, page.getPageable().getPageNumber() + 1,
                (int) Math.ceil((double) page.getTotalElements() / page.getPageable().getPageSize()),
                page.getPageable().getPageSize(), page.getTotalElements());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CustomPageable {
        String sortBy;
        int currentPageNumber;
        int totalPageNumber;
        int pageSize;
        long totalItems;
    }
}
