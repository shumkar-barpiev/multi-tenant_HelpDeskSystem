package com.shumkar.helpdesksystem.common.security;

public final class SecurityAuthorities {

	public static final String ROLE_PLATFORM_ADMIN = "ROLE_PLATFORM_ADMIN";

	public static final String ROLE_OWNER = "ROLE_OWNER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_AGENT = "ROLE_AGENT";
	public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";

	public static final String ORGANIZATION_READ = "ORGANIZATION_READ";
	public static final String ORGANIZATION_UPDATE = "ORGANIZATION_UPDATE";
	public static final String ORGANIZATION_DELETE = "ORGANIZATION_DELETE";

	public static final String MEMBER_READ = "MEMBER_READ";
	public static final String MEMBER_INVITE = "MEMBER_INVITE";
	public static final String MEMBER_UPDATE_ROLE = "MEMBER_UPDATE_ROLE";
	public static final String MEMBER_SUSPEND = "MEMBER_SUSPEND";
	public static final String MEMBER_REMOVE = "MEMBER_REMOVE";

	public static final String TICKET_CREATE = "TICKET_CREATE";
	public static final String TICKET_READ = "TICKET_READ";
	public static final String TICKET_UPDATE = "TICKET_UPDATE";
	public static final String TICKET_DELETE = "TICKET_DELETE";
	public static final String TICKET_ASSIGN = "TICKET_ASSIGN";
	public static final String TICKET_CHANGE_STATUS =
			"TICKET_CHANGE_STATUS";
	public static final String TICKET_CHANGE_PRIORITY =
			"TICKET_CHANGE_PRIORITY";
	public static final String TICKET_CLOSE = "TICKET_CLOSE";
	public static final String TICKET_REOPEN = "TICKET_REOPEN";

	public static final String COMMENT_CREATE = "COMMENT_CREATE";
	public static final String COMMENT_READ = "COMMENT_READ";
	public static final String COMMENT_UPDATE_OWN =
			"COMMENT_UPDATE_OWN";
	public static final String COMMENT_DELETE_OWN =
			"COMMENT_DELETE_OWN";
	public static final String INTERNAL_COMMENT_CREATE =
			"INTERNAL_COMMENT_CREATE";
	public static final String INTERNAL_COMMENT_READ =
			"INTERNAL_COMMENT_READ";

	// Ticket category permissions
	public static final String CATEGORY_READ = "CATEGORY_READ";
	public static final String CATEGORY_CREATE = "CATEGORY_CREATE";
	public static final String CATEGORY_UPDATE = "CATEGORY_UPDATE";
	public static final String CATEGORY_DELETE = "CATEGORY_DELETE";

	// SLA permissions
	public static final String SLA_POLICY_READ = "SLA_POLICY_READ";
	public static final String SLA_POLICY_CREATE = "SLA_POLICY_CREATE";
	public static final String SLA_POLICY_UPDATE = "SLA_POLICY_UPDATE";
	public static final String SLA_POLICY_DELETE = "SLA_POLICY_DELETE";

	public static final String NOTIFICATION_READ = "NOTIFICATION_READ";
	public static final String NOTIFICATION_MARK_READ =
			"NOTIFICATION_MARK_READ";

	public static final String REPORT_READ = "REPORT_READ";

	public static final String PLATFORM_USER_READ =
			"PLATFORM_USER_READ";
	public static final String PLATFORM_USER_UPDATE =
			"PLATFORM_USER_UPDATE";
	public static final String PLATFORM_ORGANIZATION_READ =
			"PLATFORM_ORGANIZATION_READ";
	public static final String PLATFORM_ORGANIZATION_UPDATE =
			"PLATFORM_ORGANIZATION_UPDATE";

	private SecurityAuthorities() {
	}
}