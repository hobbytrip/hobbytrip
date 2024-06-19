package com.capstone.notificationservice.domain.server.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MentionType {

    EVERYONE("MENTION-EVERYONE"), HERE("MENTION-HERE"), USER("MENTION-USER"), NOALERT("NO_ALERT");

    private final String type;
}
