package networking;
/**
 * 
 * this class represents the commands for the communication;
 * initially defined as enum but then changed to class to avoid confusion with the toString method and issues during switch case;
 *
 */
public class Commands {
			public static final String MAKE_MOVE = "MAKE_MOVE";
			public static final String GAME = "GAME";
			public static final String UPDATE = "UPDATE";
			public static final String ACCEPT = "ACCEPT";
			public static final String DECLINE = "DECLINE";
			public static final String JOIN = "JOIN";
			public static final String DISCONNECT_LOBBY = "DISCONNECT_LOBBY";
			public static final String READY_LOBBY= "READY_LOBBY"; //use to start the game
			public static final String END_TURN = "END_TURN";
			public static final String CONNECTED = "CONNECTED";
			public static final String START = "START";
			public static final String GAME_FINISH = "GAME_FINISH";
			public static final String CHAT = "CHAT";
			public static final String LEADERBOARD = "LEADERBOARD";
			public static final String INITIALIZE = "INITIALIZE"; // used for client-server handshake;
	
			public static final String DELIMITER = ";"; // This String shows what the delimiter symbol is 
	

	
	

}
