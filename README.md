# AD_8_LFT
Current documentation for websockets:
Access point is {server}/websocket/{username of current user}
Messages format is as following:
To server:
"g:" - get cached messages for the current user - use inside onOpen method on client-side
"m:{username2}&{mes}" - cache message {mes} from current user sent to {username2} or send them in real time if user is online
"L:" - get the next person on the swiping list
"F:{username}" - the current user has chosen {username} as a match


Answer format the server will send to the client:
"m:{username of who sent message}&{message}" for messages to the current user. If many, each next starts on the next line (so scanner.nextLine should work), the code is ONLY at the beginning.
"L:{full profile object separated by & according to how it is in the database}" - the profile that should be displayed at the swiping list for the current user
"E:" if there are no more people in the list to display
"F:{username}" if the current user has a match with {username} (so they should have a chat together)
# mavenBuild CI/CD test