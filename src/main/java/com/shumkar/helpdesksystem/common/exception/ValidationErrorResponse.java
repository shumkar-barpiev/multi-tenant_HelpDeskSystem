package com.shumkar.helpdesksystem.common.exception;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponse(
		Instant timestamp,
		int status,
		String code,
		String message,
		String path,
		Map<String, String> fieldErrors
) {
}
