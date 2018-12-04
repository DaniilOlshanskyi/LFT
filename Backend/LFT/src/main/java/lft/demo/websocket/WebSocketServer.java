package lft.demo.websocket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.socket.server.standard.*;
//import org.springframework.*;

import lft.demo.*;
import lft.demo.games.*;
import lft.demo.user_has_games.*;
//import lft.demo.Profiles;
//import lft.demo.UserRepository;
//import lft.demo.user_has_games.*;
//import lft.demo.games.*;

/**
 * A class instantiating a websocket connection. Handles chats and swiping list mechanics. 
 * 
 * @author Daniil Olshanskyi
 *
 */
@Service
@ServerEndpoint(value="/websocket/{username}", configurator = CustomSpringConfigurator.class)
public class WebSocketServer extends SpringBeanAutowiringSupport {
	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();

	@Autowired
	private UserRepository userRepository;
		
//	@Autowired
//	private TestClass testClass;
	
	@Autowired
	private HasGamesRepository hasGamesRepository;

	// Counter to keep track of the list
	int listCounter;
	// List itself
	LinkedList<Profiles> list;

	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	/**
	 * Method that is called when the WebSocket connection is opened.
	 * 
	 * @param session user session, provided by Spring.
	 * @param username username for user who opens the connection, path parameter.
	 * @throws IOException for incorrect IO.
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		logger.info("Entered into Open. User: " + username);

		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);
		
		// Create lists to pre-generate swiping lists
		Profiles user = userRepository.findByprofUsername(username);
		logger.info("****!!!!****Got user profile. User ID: " + user.getID().intValue());
		List<HasGames> listGames = hasGamesRepository.findByprofID(user.getID().intValue());
		list = new LinkedList<Profiles>();
   
		for (int k = 0; k < listGames.size(); k++) {
			int gameid = listGames.get(k).getgameId();
			List<HasGames> listProfilesWithGame = hasGamesRepository.findBygameId(gameid);
			for (int i = 0; i < listProfilesWithGame.size(); i++) {
				Profiles match = userRepository.findById(listProfilesWithGame.get(i).getprofID()).get();
				if (!list.contains(match) && match.getprofSuspend() == 0 && (!match.getprofUsername().equals(username))) {
					//logger.info("MATH AND USERNAME:" + match.getprofUsername() + ":::" + username);
					list.add(match);
				}
			}
		}
		listCounter = 0;
	}
	/**
	 * Method that is called when the new message is received. Handles all parsing the message and then acts accordingly. 
	 * 
	 * @param session session with current user.
	 * @param message the message received from the current user.
	 * @throws IOException for incorrect IO.
	 */
	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		// Handle new messages
		String username = sessionUsernameMap.get(session);
		logger.info("Entered into Message: Got Message from: " + username + " " + message);

		String code = message.substring(0, 2);
		// If it is a request to check for new messages:
		if (code.equals("g:")) {
			// Get all old messages
			File folder = new File("/home/LFT/chats/");
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				String fileName = listOfFiles[i].getName();
				String receiver = fileName.substring(fileName.indexOf("|") + 1, fileName.indexOf(".txt"));
				if (receiver.equals(username)) {
					newMessages(session, fileName.substring(0, fileName.indexOf("|")), username);
				}
			}
			folder = new File("matches/");
			listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				String fileName = listOfFiles[i].getName();
				if (fileName.substring(0,fileName.indexOf(".txt")).equals(username)) {
					byte[] bytes = Files.readAllBytes(listOfFiles[i].toPath());
					String fileContents = new String(bytes, "UTF-8");
					String[] s = fileContents.split("&");
					logger.info(s.toString());
					for (int k = 0; k<s.length; k++) {
						sendMessageToPArticularUser(username, "F:"+s[k]);
					}
				}
			}
			
		} // Else, if it is a new message sent
		else if (code.equals("m:")) {
			BufferedWriter writer = null;
			try {
				// Open chat from this user to that user
				String receiver = message.substring(2, message.indexOf("&"));
				File file = new File("chats/" + username + "|" + receiver + ".txt");
				// If file exists - append, if not - create and write to it
				if (file.exists()) {
					writer = new BufferedWriter(new FileWriter(file, true));
				} else {
					writer = new BufferedWriter(new FileWriter(file));
				}
				// Get the real message
				String realMessage = "m:" + username + "&" + message.substring(message.indexOf("&") + 1);
				writer.write(realMessage);
				// Check if the receiver is currently connected
				if (usernameSessionMap.containsKey(receiver)) {
					sendMessageToPArticularUser(receiver, realMessage);
					writer.close();
					file.delete();
				}
			} catch (Exception e) {
				logger.info("Exception in onMessage!");
			} // Close writer anyway
			finally {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		} else if (code.equals("L:")) {
			if (listCounter<list.size()) {
				sendMessageToPArticularUser(username, "L:" + list.get(listCounter).toString());
				listCounter++;
			} else {
				sendMessageToPArticularUser(username, "E:");
			}
		} else if (code.equals("F:")) {
			String match = message.substring(2);
			matchTwoUsers(username, match);
		}
	}

	/**
	 * Method that handles connetion closing. Removes current user and sessions from the map.
	 * 
	 * @param session current user session.
	 * @throws IOException for incorrect IO.
	 */
	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
	}

	/**
	 * Exception handler for any errors that can occur. 
	 * 
	 * @param session current user session.
	 * @param throwable an exception that occurred in websocket.
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error" + throwable.toString());
		throwable.printStackTrace(System.out);
	}

	private void sendMessageToPArticularUser(String username, String message) {
		try {
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	private static void broadcast(String message) throws IOException {
		sessionUsernameMap.forEach((session, username) -> {
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Get new messages from username to username2
	 * 
	 * @param session
	 * @param username
	 * @param username2
	 */
	private void newMessages(Session session, String username, String username2) {
		logger.info("Checking for old messages from " + username + " to " + username2 + ".txt");
		String message = "m:" + username2 + "&";
		// Open cached message file
		File file = new File("/home/LFT/chats/" + username + "|" + username2 + ".txt");
		try {
			Scanner sc = new Scanner(file);
			// Get lines of messages
			while (sc.hasNextLine()) {
				message += sc.nextLine() + "\n";
			}
			sc.close();
			// Send retrieved chat to user who sent the request
			sendMessageToPArticularUser(username2, message);
			// Delete the file since it's not needed anymore
			file.delete();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	private void matchTwoUsers(String username, String match) {
		boolean written = false;
		// Get list of files with matches
		File folder = new File("/home/LFT/matches/");
		File[] listOfFiles = folder.listFiles();
		// Search for the file that is for the one who the person matched
		for (int i = 0; i < listOfFiles.length; i++) {
			String fileNameWithExt = listOfFiles[i].getName();
			String fileName = fileNameWithExt.substring(0, fileNameWithExt.indexOf(".txt"));
			if (fileName.equals(match)) {
				String fileContents = "";
				// Read people that "match" has chosen
				try {
					byte[] bytes = Files.readAllBytes(listOfFiles[i].toPath());
					fileContents = new String(bytes, "UTF-8");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (fileContents.contains(username)) {
					sendMessageToPArticularUser(username, "F:" + match);
				}
			} else if (fileName.equals(username)) { // If the file for this user is found - append the new match to it
				written = true;
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(listOfFiles[i], true));
					writer.write("&" + match);
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if (!written) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("matches/" +username+".txt"));
				writer.write(match);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
