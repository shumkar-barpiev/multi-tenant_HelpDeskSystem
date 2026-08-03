package com.shumkar.helpdesksystem.user.service.implement;

import com.shumkar.helpdesksystem.common.exception.DuplicateResourceException;
import com.shumkar.helpdesksystem.common.exception.InvalidPasswordException;
import com.shumkar.helpdesksystem.common.exception.ResourceNotFoundException;
import com.shumkar.helpdesksystem.user.dto.UserCreateRequest;
import com.shumkar.helpdesksystem.user.dto.UserResponse;
import com.shumkar.helpdesksystem.user.dto.UserUpdateRequest;
import com.shumkar.helpdesksystem.user.entity.User;
import com.shumkar.helpdesksystem.user.mapper.UserMapper;
import com.shumkar.helpdesksystem.user.repository.UserRepository;
import com.shumkar.helpdesksystem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserResponse createUser(UserCreateRequest request) {
		if (existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("User already exists with email: " + request.getEmail());
		}
		if (existsByUsername(request.getUsername())) {
			throw new DuplicateResourceException("User already exists with username: " + request.getUsername());
		}

		User user = userMapper.toEntity(request);
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		User savedUser = userRepository.save(user);

		return userMapper.toResponse(savedUser);
	}

	@Override
	public UserResponse getUserById(UUID userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		return userMapper.toResponse(user);
	}

	@Override
	public UserResponse getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
		return userMapper.toResponse(user);
	}

	@Override
	public UserResponse getCurrentUser() {
		Authentication authentication = getAuth();
		
		String identifier = authentication.getName();
		User user = userRepository.findByEmail(identifier)
				.orElseGet(() -> userRepository.findByUsername(identifier)
						.orElseThrow(() -> new ResourceNotFoundException("Current user not found with identifier: " + identifier)));

		return userMapper.toResponse(user);
	}

	@Override
	public Page<UserResponse> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable)
				.map(userMapper::toResponse);
	}

	@Override
	@Transactional
	public UserResponse updateUser(UUID userId, UserUpdateRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
			if (existsByEmail(request.getEmail())) {
				throw new DuplicateResourceException("User already exists with email: " + request.getEmail());
			}
		}

		if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
			if (existsByUsername(request.getUsername())) {
				throw new DuplicateResourceException("User already exists with username: " + request.getUsername());
			}
		}

		userMapper.updateEntity(request, user);
		User updatedUser = userRepository.save(user);

		return userMapper.toResponse(updatedUser);
	}

	@Override
	@Transactional
	public UserResponse updateCurrentUser(UserUpdateRequest request) {
		Authentication authentication = getAuth();

		String identifier = authentication.getName();
		User user = userRepository.findByEmail(identifier)
				.orElseGet(() -> userRepository.findByUsername(identifier)
						.orElseThrow(() -> new ResourceNotFoundException("Current user not found with identifier: " + identifier)));

		return updateUser(user.getId(), request);
	}

	@Override
	@Transactional
	public void changePassword(UUID userId, String currentPassword, String newPassword) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
			throw new InvalidPasswordException("Current password does not match");
		}

		user.setPasswordHash(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void verifyEmail(UUID userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		user.setEmailVerified(true);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void recordSuccessfulLogin(UUID userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		user.setLastLoginAt(Instant.now());
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void deactivateUser(UUID userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		user.setActive(false);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void reactivateUser(UUID userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		user.setActive(true);
		userRepository.save(user);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	private Authentication getAuth() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
			throw new IllegalStateException("No authenticated user found in security context");
		}
		return authentication;
	}
}
