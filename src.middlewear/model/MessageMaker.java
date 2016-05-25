package model;

public class MessageMaker {
	
	private static final String MESSAGE_PREFIX = "#::";
	private static final String MESSAGE_POSTFIX = "::";
	private static final String CONNECT_MESSAGE = "connect";
	private static final String USERNAME_MESSAGE = "username";
	private static final String PLAYER_ID_MESSAGE = "playerid";
	private static final String START_MATCH_MESSAGE = "startmatch";
	private static final String LEVEL_UP_MESSAGE = "levelup";
	private static final String UPDATE_MATCH_MESSAGE = "updatematch";
	private static final String COMPLETE_MATCH_MESSAGE = "completematch";
	private static final String END_MATCH_MESSAGE = "endmatch";
	
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
	
	public static String getPlayerIdMessage(String message) {
		return (MESSAGE_PREFIX + PLAYER_ID_MESSAGE + MESSAGE_POSTFIX + message);
	}
	
	public static String getStartMatchMessage() {
		return (MESSAGE_PREFIX + START_MATCH_MESSAGE + MESSAGE_POSTFIX);
	}
	
	public static String getLevelUpMessage(int level) {
		return MESSAGE_PREFIX + LEVEL_UP_MESSAGE + MESSAGE_POSTFIX + level;
	}
	
	public static String getUpdateMatchMessage(String message) {
		return (MESSAGE_PREFIX + UPDATE_MATCH_MESSAGE + MESSAGE_POSTFIX + message);
	}
	
	public static String getCompleteMatchMessage(String message) {
		return (MESSAGE_PREFIX + COMPLETE_MATCH_MESSAGE + MESSAGE_POSTFIX + message);
	}
	
	public static String getEndMatchMessage() {
		return (MESSAGE_PREFIX + END_MATCH_MESSAGE + MESSAGE_POSTFIX);
	}
	
	public static boolean isConnectMessage(String message) {
		return isMessageType(message, CONNECT_MESSAGE);
	}
	
	public static boolean isUsernameMessage(String message) {
		return isMessageType(message, USERNAME_MESSAGE);
	}
	
	public static boolean isPlayerIdMessage(String message) {
		return isMessageType(message, PLAYER_ID_MESSAGE);
	}
	
	public static boolean isStartMatchMessage(String message) {
		return isMessageType(message, START_MATCH_MESSAGE);
	}
	
	public static boolean isLevelUpMessage(String message) {
		return isMessageType(message, LEVEL_UP_MESSAGE);
	}
	
	public static boolean isUpdateMatchMessage(String message) {
		return isMessageType(message, UPDATE_MATCH_MESSAGE);
	}
	
	public static boolean isCompleteMatchMessage(String message) {
		return isMessageType(message, COMPLETE_MATCH_MESSAGE);
	}
	
	public static boolean isEndMatchMessage(String message) {
		return isMessageType(message, END_MATCH_MESSAGE);
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
