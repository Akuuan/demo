package com.akuan.wechat.service;

import lombok.SneakyThrows;

public interface MessageService {
    @SneakyThrows
    void pushPendingMessage(Long processId, Long userId, String taskId);

    @SneakyThrows
    void pushProcessedMessage(Long processId, Long userId, Integer status);
}
