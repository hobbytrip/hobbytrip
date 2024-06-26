package capstone.communityservice.domain.category.service;

import capstone.communityservice.domain.category.dto.CategoryCreateRequestDto;
import capstone.communityservice.domain.category.dto.CategoryDeleteRequestDto;
import capstone.communityservice.domain.category.dto.CategoryResponseDto;
import capstone.communityservice.domain.category.dto.CategoryUpdateRequestDto;
import capstone.communityservice.domain.category.entity.Category;
import capstone.communityservice.domain.category.exception.CategoryException;
import capstone.communityservice.domain.category.repository.CategoryRepository;
import capstone.communityservice.domain.dm.exception.DmException;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.service.ServerQueryService;
import capstone.communityservice.global.common.dto.kafka.CommunityCategoryEventDto;
import capstone.communityservice.global.common.dto.kafka.CommunityChannelEventDto;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCommandService {
    private static final String categoryKafkaTopic = "communityCategoryEventTopic";

    private final KafkaTemplate<String, CommunityCategoryEventDto> categoryKafkaTemplate;

    private final CategoryRepository categoryRepository;
    private final ServerQueryService serverQueryService;

    public CategoryResponseDto save(Category category) {
        Category newCategory = categoryRepository.save(category);

        categoryKafkaTemplate.send(categoryKafkaTopic, CommunityCategoryEventDto.of("category-create",
                newCategory,
                newCategory.getServer().getId()
                )
        );

        printKafkaLog("create");

        return CategoryResponseDto.of(newCategory);
    }

    public CategoryResponseDto create(CategoryCreateRequestDto requestDto) {
        Server findServer = validateManagerInCategory(requestDto.getServerId(), requestDto.getUserId());

        return save(Category.of(
                findServer, requestDto.getName())
        );
    }

    public CategoryResponseDto update(CategoryUpdateRequestDto requestDto) {
        validateManagerInCategory(requestDto.getServerId(), requestDto.getUserId());

        Category findCategory = validateCategory(requestDto.getCategoryId());
        findCategory.setName(requestDto.getName());

        categoryKafkaTemplate.send(categoryKafkaTopic, CommunityCategoryEventDto.of("category-update", findCategory, requestDto.getServerId()));

        printKafkaLog("update");

        return CategoryResponseDto.of(findCategory);
    }

    public void delete(CategoryDeleteRequestDto requestDto) {
        validateManagerInCategory(requestDto.getServerId(), requestDto.getUserId());

        Category findCategory = validateCategory(requestDto.getCategoryId());

        categoryKafkaTemplate.send(categoryKafkaTopic, CommunityCategoryEventDto.of("category-delete", findCategory, requestDto.getServerId()));

        printKafkaLog("delete");

        categoryRepository.delete(findCategory);
    }

    private Server validateManagerInCategory(Long serverId, Long userId) {
        Server findServer = serverQueryService.validateExistServer(serverId);

        serverQueryService.validateManager(
                findServer.getManagerId(), userId
        );

        return findServer;
    }

    private Category validateCategory(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DmException(Code.NOT_FOUND, "Not Found Category"));
    }

    private void printKafkaLog(String type) {
        log.info("Kafka event send about Category {}", type);
    }
}
