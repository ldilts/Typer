package model;

public class MessageMaker {
	
	private static final String MESSAGE_PREFIX = "#::";
	private static final String MESSAGE_POSTFIX = "::";
	private static final String CONNECT_MESSAGE = "connect";
	private static final String USERNAME_MESSAGE = "username";
	private static final String START_MATCH_MESSAGE = "startmatch";
	
	private static final String delims = "[#:]+";
	
	/*
	 * Public helper methods
	 */
	
	public static String getMessage(String message) {
		String[] tokens = message.split(delims);
		
		if (tokens.length < 3) {
			return "";
		} else {
			return tokens[2];
		}
	}
	
	public static String getConnectMessage(String message) {
		return (MESSAGE_PREFIX + CONNECT_MESSAGE + MESSAGE_POSTFIX + message);
	}
	
	public static String getUsernameMessage(String message) {
		return (MESSAGE_PREFIX + USERNAME_MESSAGE + MESSAGE_POSTFIX + message);
	}
	
	public static String getStartMatchMessage() {
		return (MESSAGE_PREFIX + START_MATCH_MESSAGE + MESSAGE_POSTFIX);
	}
	
	public static boolean isConnectMessage(String message) {
		return isMessageType(message, CONNECT_MESSAGE);
	}
	
	public static boolean isUsernameMessage(String message) {
		return isMessageType(message, USERNAME_MESSAGE);
	}
	
	public static boolean isStartMatchMessage(String message) {
		return isMessageType(message, START_MATCH_MESSAGE);
	}
	
	/* 
	 * Private helper methods
	 */
	
	private static boolean isMessageType(String message, String type) {
		return getMessageType(message).equals(type);
	}
	
	private static String getMessageType(String message) {
		String[] tokens = message.split(delims);
		
		if (tokens.length < 2) {
			return "";
		} else {
			return tokens[1];
		}
	}

}
