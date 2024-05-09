package capstone.communityservice.global.common.dto;

import lombok.*;
import org.springframework.data.domain.Sort;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SortResponse {
    private boolean sorted;
    private String direction;
    private String orderProperty;

    public static SortResponse of(Sort sort) {
        SortResponse sortResponse = new SortResponse();
        if (sort != null && sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            sortResponse.setSortResponse(order.getDirection().name(), order.getProperty());
        }

        return sortResponse;
    }

    private void setSortResponse(String direction, String orderProperty){
        this.sorted = true;
        this.direction = direction;
        this.orderProperty = orderProperty;
    }

}

