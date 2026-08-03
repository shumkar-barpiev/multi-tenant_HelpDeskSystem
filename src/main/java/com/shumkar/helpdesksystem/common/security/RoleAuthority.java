package com.shumkar.helpdesksystem.common.security;

import com.shumkar.helpdesksystem.organization.entity.type.UserRole;

import java.util.Set;

import static com.shumkar.helpdesksystem.common.security.SecurityAuthorities.*;

public final class RoleAuthority {

	private RoleAuthority() {
	}

	public static Set<String> getAuthorities(UserRole role) {
		return switch (role) {
			case OWNER -> ownerAuthorities();
			case ADMIN -> adminAuthorities();
			case AGENT -> agentAuthorities();
			case CUSTOMER -> customerAuthorities();
		};
	}

	private static Set<String> ownerAuthorities() {
		return Set.of(
				ROLE_OWNER,
				ORGANIZATION_READ,
				ORGANIZATION_UPDATE,
				ORGANIZATION_DELETE,
				MEMBER_READ,
				MEMBER_INVITE,
				MEMBER_UPDATE_ROLE,
				MEMBER_SUSPEND,
				MEMBER_REMOVE,
				TICKET_CREATE,
				TICKET_READ,
				TICKET_UPDATE,
				TICKET_DELETE,
				TICKET_ASSIGN,
				TICKET_CHANGE_STATUS,
				TICKET_CHANGE_PRIORITY,
				TICKET_CLOSE,
				TICKET_REOPEN,
				COMMENT_CREATE,
				COMMENT_READ,
				COMMENT_UPDATE_OWN,
				COMMENT_DELETE_OWN,
				INTERNAL_COMMENT_CREATE,
				INTERNAL_COMMENT_READ,
				CATEGORY_READ,
				CATEGORY_CREATE,
				CATEGORY_UPDATE,
				CATEGORY_DELETE,
				SLA_POLICY_READ,
				SLA_POLICY_CREATE,
				SLA_POLICY_UPDATE,
				SLA_POLICY_DELETE,
				NOTIFICATION_READ,
				NOTIFICATION_MARK_READ,
				REPORT_READ
		);
	}

	private static Set<String> adminAuthorities() {
		return Set.of(
				ROLE_ADMIN,
				ORGANIZATION_READ,
				ORGANIZATION_UPDATE,
				MEMBER_READ,
				MEMBER_INVITE,
				MEMBER_UPDATE_ROLE,
				MEMBER_SUSPEND,
				MEMBER_REMOVE,
				TICKET_CREATE,
				TICKET_READ,
				TICKET_UPDATE,
				TICKET_DELETE,
				TICKET_ASSIGN,
				TICKET_CHANGE_STATUS,
				TICKET_CHANGE_PRIORITY,
				TICKET_CLOSE,
				TICKET_REOPEN,
				COMMENT_CREATE,
				COMMENT_READ,
				COMMENT_UPDATE_OWN,
				COMMENT_DELETE_OWN,
				INTERNAL_COMMENT_CREATE,
				INTERNAL_COMMENT_READ,
				CATEGORY_READ,
				CATEGORY_CREATE,
				CATEGORY_UPDATE,
				CATEGORY_DELETE,
				SLA_POLICY_READ,
				SLA_POLICY_CREATE,
				SLA_POLICY_UPDATE,
				SLA_POLICY_DELETE,
				NOTIFICATION_READ,
				NOTIFICATION_MARK_READ,
				REPORT_READ
		);
	}

	private static Set<String> agentAuthorities() {
		return Set.of(
				ROLE_AGENT,
				ORGANIZATION_READ,
				MEMBER_READ,
				TICKET_CREATE,
				TICKET_READ,
				TICKET_UPDATE,
				TICKET_ASSIGN,
				TICKET_CHANGE_STATUS,
				TICKET_CHANGE_PRIORITY,
				TICKET_CLOSE,
				TICKET_REOPEN,
				COMMENT_CREATE,
				COMMENT_READ,
				COMMENT_UPDATE_OWN,
				COMMENT_DELETE_OWN,
				INTERNAL_COMMENT_CREATE,
				INTERNAL_COMMENT_READ,
				CATEGORY_READ,
				SLA_POLICY_READ,
				NOTIFICATION_READ,
				NOTIFICATION_MARK_READ
		);
	}

	private static Set<String> customerAuthorities() {
		return Set.of(
				ROLE_CUSTOMER,
				ORGANIZATION_READ,
				TICKET_CREATE,
				TICKET_READ,
				COMMENT_CREATE,
				COMMENT_READ,
				COMMENT_UPDATE_OWN,
				COMMENT_DELETE_OWN,
				CATEGORY_READ,
				NOTIFICATION_READ,
				NOTIFICATION_MARK_READ
		);
	}
}