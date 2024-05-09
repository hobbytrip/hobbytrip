package capstone.communityservice.global.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class SliceResponseDto<T> {
    private final List<T> content;
    private final SortResponse sort;
    private final int currentPage;
    private final int size;
    private final boolean first;
    private final boolean last;

    public SliceResponseDto(Slice<T> sliceContent){
        this.content = sliceContent.getContent();
        this.sort = SortResponse.of(sliceContent.getSort());
        this.currentPage = sliceContent.getNumber();
        this.size = sliceContent.getSize();
        this.first = sliceContent.isFirst();
        this.last = sliceContent.isLast();
    }
}
