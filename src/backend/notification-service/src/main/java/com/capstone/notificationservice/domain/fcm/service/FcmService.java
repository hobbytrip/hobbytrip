package com.capstone.notificationservice.domain.fcm.service;

import com.capstone.notificationservice.domain.fcm.dto.FcmSendDto;
import com.capstone.notificationservice.domain.fcm.dto.FcmTokenDto;
import com.capstone.notificationservice.domain.fcm.exception.FcmException;
import com.capstone.notificationservice.global.common.dto.DataResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface FcmService {
    DataResponseDto<Object> sendMessageTo(FcmSendDto fcmSendDto) throws FcmException;

    @Transactional
    DataResponseDto<Object> saveFcmToken(FcmTokenDto fcmTokenDto);
}
