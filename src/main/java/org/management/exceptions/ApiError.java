package org.management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;

    private Integer statusCode;

    private String statusName;

    private String reason;

    private List<String> errors = new ArrayList<>();
}
