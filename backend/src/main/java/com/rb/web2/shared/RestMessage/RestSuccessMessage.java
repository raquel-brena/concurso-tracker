package com.rb.web2.shared.RestMessage;

import java.util.Optional;

public record RestSuccessMessage(String message, Optional<Object> data) {
    public RestSuccessMessage(String message) {
        this(message, Optional.empty());
    }

    public RestSuccessMessage(String message, Object data) {
        this(message, Optional.of(data));
    }
}
