package capstone.communityservice.domain.category.repository;

import capstone.communityservice.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
