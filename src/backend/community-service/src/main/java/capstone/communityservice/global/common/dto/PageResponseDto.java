package capstone.communityservice.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<D> {
    private int numberOfElements;
    private int totalPages;
    private boolean hasNext;
    private List<D> data;

    public static <E, D> PageResponseDto of(Page<E> entity, Function<E, D> makeDto) {
        List<D> dto = convertToDto(entity, makeDto);
        return new PageResponseDto(entity.getNumberOfElements(), entity.getTotalPages(), entity.hasNext(), dto);
    }

    private static <E, D> List<D> convertToDto(Page<E> entity, Function<E, D> makeDto) {
        return entity.getContent().stream()
                .map(e -> makeDto.apply(e))
                .collect(Collectors.toList());
    }
}