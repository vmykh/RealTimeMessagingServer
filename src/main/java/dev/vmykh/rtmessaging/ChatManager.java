package dev.vmykh.rtmessaging;

/**
 * ChatManager is responsible for managing (creating, joining, receiving) {@link Chat} objects
 */
public interface ChatManager {

	/**
	 * Checks whether chat with specified name exists
	 * @param name chat name
	 * @return true if chat with specified name exists, and false otherwise
	 */
	boolean chatExists(String name);

	/**
	 * Creates new chat with specified name
	 * @param name chat name
	 * @throws IllegalStateException if chat with specified name already exists
	 */
	void createChat(String name) throws IllegalStateException;

	/**
	 * Returns chat with specified name
	 * @param name chat name
	 * @return chat with specifed name
	 * @throws IllegalArgumentException if there is no chat with specified name
	 */
	Chat getChat(String name) throws IllegalArgumentException;
}
