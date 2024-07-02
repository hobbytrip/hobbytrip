package capstone.communityservice.domain.category.service;

import capstone.communityservice.domain.category.dto.request.CategoryCreateRequest;
import capstone.communityservice.domain.category.dto.request.CategoryDeleteRequest;
import capstone.communityservice.domain.category.dto.response.CategoryResponse;
import capstone.communityservice.domain.category.dto.request.CategoryUpdateRequest;
import capstone.communityservice.domain.category.entity.Category;
import capstone.communityservice.domain.category.repository.CategoryRepository;
import capstone.communityservice.domain.dm.exception.DmException;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.service.ServerQueryService;
import capstone.communityservice.global.common.dto.kafka.CommunityCategoryEventDto;
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

    public CategoryResponse save(Category category) {
        Category newCategory = categoryRepository.save(category);

        categoryKafkaTemplate.send(categoryKafkaTopic, CommunityCategoryEventDto.of("category-create",
                newCategory,
                newCategory.getServer().getId()
                )
        );

        printKafkaLog("create");

        return CategoryResponse.of(newCategory);
    }

    public CategoryResponse create(CategoryCreateRequest request) {
        Server findServer = validateManagerInCategory(request.getServerId(), request.getUserId());

        return save(Category.of
                (
                    findServer, request.getName()
                )
        );
    }

    public CategoryResponse update(CategoryUpdateRequest request) {
        validateManagerInCategory(request.getServerId(), request.getUserId());

        Category findCategory = validateCategory(request.getCategoryId());
        findCategory.setName(request.getName());

        categoryKafkaTemplate.send(
                categoryKafkaTopic,
                CommunityCategoryEventDto.of(
                        "category-update",
                        findCategory,
                        request.getServerId()
                )
        );

        printKafkaLog("update");

        return CategoryResponse.of(findCategory);
    }

    public void delete(CategoryDeleteRequest request) {
        validateManagerInCategory(request.getServerId(), request.getUserId());

        Category findCategory = validateCategory(request.getCategoryId());

        categoryKafkaTemplate.send(categoryKafkaTopic, CommunityCategoryEventDto.of("category-delete", findCategory, request.getServerId()));

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
