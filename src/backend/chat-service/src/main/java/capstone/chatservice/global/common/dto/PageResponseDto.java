package capstone.chatservice.global.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<D> {
    private int numberOfElements;
    private int totalPages;
    private boolean hasNext;
    private List<D> data;

    public static <E> PageResponseDto of(Page<E> entity) {
        return new PageResponseDto(entity.getNumberOfElements(), entity.getTotalPages(), entity.hasNext(),
                entity.getContent());
    }
}