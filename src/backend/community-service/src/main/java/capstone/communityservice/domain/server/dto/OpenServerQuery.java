package capstone.communityservice.domain.server.dto;

public interface OpenServerQuery {
    Long getId();
    Long getManagerId();
    String getProfile();
    String getDescription();
    boolean isOpen();

    Long getUserCount();
}
