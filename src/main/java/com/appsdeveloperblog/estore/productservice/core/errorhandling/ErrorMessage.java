package com.appsdeveloperblog.estore.productservice.core.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ErrorMessage {
    private final LocalDateTime timestamp;
    private final String message;
}
