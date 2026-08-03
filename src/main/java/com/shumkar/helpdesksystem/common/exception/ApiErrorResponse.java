package com.shumkar.helpdesksystem.common.exception;

import java.time.Instant;

public record ApiErrorResponse(
		Instant timestamp,
		int status,
		String error,
		String code,
		String message,
		String path
) {
}
