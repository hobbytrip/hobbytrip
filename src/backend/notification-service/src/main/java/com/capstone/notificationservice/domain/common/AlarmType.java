package com.capstone.notificationservice.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    DM("DM-ALARM"), SERVER("SERVER-ALARM");

    private final String type;
}