package com.tiger.crm.repository.dto.alert;

public enum AlertType {
    TICKET_STATUS("TICKET_STATUS"),    // 요청 상태
    TICKET_COMMENT("TICKET_COMMENT"),  // 요청 댓글
    NOTICE("NOTICE");                  // 게시판

    private final String type;

    AlertType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
