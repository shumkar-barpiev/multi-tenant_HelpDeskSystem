package com.shumkar.helpdesksystem.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {

		String authorizationHeader =
				request.getHeader("Authorization");

		if (authorizationHeader == null
				|| !authorizationHeader.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);
			return;
		}

		String token = authorizationHeader.substring(7);

		try {
			/*
			 * Later:
			 * 1. Validate the token.
			 * 2. Extract the user ID/email.
			 * 3. Load the user.
			 * 4. Create an Authentication object.
			 * 5. Put it in SecurityContextHolder.
			 */

			filterChain.doFilter(request, response);

		} catch (Exception exception) {
			SecurityContextHolder.clearContext();

			response.setStatus(
					HttpServletResponse.SC_UNAUTHORIZED
			);
		}
	}
}