package com.williamsilva.algafoodapi.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private final Integer status;
    private final String type;
    private final String title;
    private final String detail;

    private final String userMessage;
    private final LocalDateTime timestamp;

    public Problem(Builder builder) {
        this.status = builder.status;
        this.type = builder.type;
        this.title = builder.title;
        this.detail = builder.detail;
        this.userMessage = builder.userMessage;
        this.timestamp = builder.timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private Integer status;
        private String type;
        private String title;
        private String detail;

        private String userMessage;
        private LocalDateTime timestamp;

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder userMessage(String userMessage) {
            this.userMessage = userMessage;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Problem build() {
            return new Problem(this);
        }
    }
}
