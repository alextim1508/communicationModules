package com.alextim.communicationModules.core.serviceMessage;


public enum ServiceMessageType {
    SERVICE_MANAGEMENT ("Управляющее сервисное сообщение"),
    SERVICE_REPLY("Ответное сервисное сообщение"),
    SERVICE_UNKNOWN("Неизвестное  сервисное сообщение");

    private final String title;

    ServiceMessageType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
