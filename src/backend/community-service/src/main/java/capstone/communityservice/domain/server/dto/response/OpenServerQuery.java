package capstone.communityservice.domain.server.dto.response;

public interface OpenServerQuery {
    Long getId();
    Long getManagerId();
    String getProfile();
    String getDescription();
    boolean isOpen();
    Long getUserCount();
}
