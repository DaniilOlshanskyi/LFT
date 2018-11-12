package lft.demo.websocket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {

	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();

	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		logger.info("Entered into Open. User: " + username + " connected");

		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);

	}

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
		} // Else, if it is a new message sent
		else if (code.equals("m:")) {
			BufferedWriter writer = null;
			try {
				// Open chat from this user to that user
				String receiver = message.substring(2, message.indexOf("@"));
				File file = new File("chats/" + username + "|" + receiver + ".txt");
				// If file exists - append, if not - create and write to it
				if (file.exists()) {
					writer = new BufferedWriter(new FileWriter(file, true));
				} else {
					writer = new BufferedWriter(new FileWriter(file));
				}
				// Get the real message
				String realMessage = username + "@" + message.substring(message.indexOf("@") + 1);
				writer.write(realMessage);
				// Check if the receiver is currently connected
				if (usernameSessionMap.containsKey(receiver)) {
					sendMessageToPArticularUser(receiver, realMessage);
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
		}
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
	}

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
		logger.info("Chacking for old messages from " + username + " to " + username2 + ".txt");
		String message = username2 + "@";
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
}

//@ServerEndpoint("/websocket/{username}")
//@Component
//public class WebSocketServer {
//	private Session session;
//	private static Set<WebSocketServer> chatEndpoints = new CopyOnWriteArraySet<>();
//	private static Map<String, String> users = new HashMap();
//	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
//
//	@OnOpen
//	public void onOpen(Session session, @PathParam("username") String username) {
//		logger.info("Entered into Open");
//		this.session = session;
//		chatEndpoints.add(this);
//		users.put(session.getId(), username);
//		
//		//Get all old messages
//		File folder = new File("chats/");
//		File[] listOfFiles = folder.listFiles();
//		for (int i = 0; i < listOfFiles.length; i++) {
//			String fileName = listOfFiles[i].getName();
//			String receiver = fileName.substring(fileName.indexOf("|")+1); 
//			if (receiver.equals(username)) {
//				newMessages(session,fileName.substring(0, fileName.indexOf("|")),username);
//			}
//		}
//	}
//
//	
//	/**
//	 * Messages format: Request for new messages: "g:{username}" New message
//	 * "m:{username}@message"
//	 * 
//	 * @param session
//	 * @param message
//	 * 
//	 */
//	@OnMessage
//	public void onMessage(Session session, String message) {
//		logger.info("Entered into Mesage. Got Message:" + message);
//
//	}
//
//	@OnClose
//	public void onClose() {
//		logger.info("Entered into Close");
//		chatEndpoints.remove(this);
//		// String message="Disconnected";
//	}
//
//	@OnError
//	public void onEror() {
//		logger.info("Entered into Error");
//	}
//
//	private void sendMessageToParticularUser(Session session, String message) {
//		try {
//			session.getBasicRemote().sendText("m:" + message);
//		} catch (IOException e) {
//			logger.info("Exception: " + e.getMessage().toString());
//			e.printStackTrace();
//		}
//	}
//
//
//	public Session isSocket(String s) {
//		if (this.users.get(session.getId()).equals(s)) {
//			return this.session;
//		} else {
//			return null;
//		}
//	}
//
//}
