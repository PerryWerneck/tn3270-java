/* SPDX-License-Identifier: LGPL-3.0-or-later */

/*
 * Copyright (C) 2023 Perry Werneck <perry.werneck@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package br.app.pw3270;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

public class Terminal implements AutoCloseable {

	static {

		try {

	        System.loadLibrary("jni3270");

		} catch (UnsatisfiedLinkError e) {
    	
			// https://stackoverflow.com/questions/1611357/how-to-make-a-jar-file-that-includes-dll-files

			String ext = ".so";
			if(System.getProperty("os.name").toLowerCase().contains("win")) {
				ext = ".dll";
			}

			String tmpfile = System.getProperty("java.io.tmpdir") + "/jni3270" + ext;

			try(InputStream in = Terminal.class.getResourceAsStream("/lib/jni3270" + ext)) {

				try(OutputStream out = new FileOutputStream(tmpfile)) {
					byte[] buffer = new byte[1024];
					int length;
					while ((length = in.read(buffer)) > 0) {
						out.write(buffer, 0, length);
					}
				} catch (IOException outerr) {

					throw new java.lang.RuntimeException("Cant save JNI Module");

				}

			} catch (IOException inerr) {

				throw new java.lang.RuntimeException("Cant load JNI Module");

			}

			System.load(tmpfile);
		
		}

    }

    /**
     * Cursor position.
     */
	public class CursorPosition {

		/**
		 * Cursor row.
		 */
		public int row = 0;

		/**
		 * Cursor column.
		 */
		public int col = 0;

		CursorPosition(int r, int c) {
			row = r;
			col = c;
		}

	}
	
	public class Action implements AutoCloseable {

		public Action(Terminal terminal, String name) {
			open(terminal.hSession,name);
		}

		// Action native handler.
		private long hAction = 0;

		public native void open(long hSession, String name);
		public native void close();

		public native boolean activatable();
		public native void activate();

		public native String name();
		public native String description();
		public native String summary();

	}

	// TN3270 native handler.
	private long hSession;

	/**
	 * Build a headless TN3270 session.
	 */
	public Terminal() {
		open();
	}

	/**
	 * Build session binded to pw3270's session window.
	 *
	 * @param id pw3270 window id (A: for the first one, B: for second ... )
	 */
	public Terminal(String id) {
		open(id,"UTF-8");
	}

	/**
	 * Get the current ipc3270 version.
	 * @return String with the version of the ipc3270 library.
	 */
	public static native String getVersion();

	/**
	* Get the current ipc3270 revision.
	* @return String with the revision of the ipc3270 library.
	*/
	public static native String getRevision();

	/**
	 * Open tn3270 session.
	 * @param id Session ID ("" for headless).
	 * @param charset Local charset.
	 */
	public native void open(String id, String charset);

	/**
	 * Open headless tn3270 session with default charset (UTF-8).
	 */
	public void open() {
		open("","UTF-8");
	}

	/**
	 * Open tn3270 session with default charset (UTF-8).
	 */
	public void open(String id) {
		open(id,"UTF-8");
	}

	/**
	 * Close tn3270 session, release resources.
	 */
	public native void close();
	
	//
	// Connection
	//
	
	/**
	 *  Connect to 3270 host.
	 *  <p>
	 *  Connect to the 3270 host
	 *  <p>
	 *  URI formats:
	 *  <ul>
	 *  <li>tn3270://[HOSTNAME]:[HOSTPORT] for non SSL connections.</li>
	 *  <li>tn3270s://[HOSTNAME]:[HOSTPORT] for ssl connection.</li>
	 *  </ul>
	 * 
	 *  @param url		Host URL.
	 *  @param seconds	How many seconds to wait for a connection.
	 */
	public native void connect(String url, int seconds);	

	/**
	 * Connect to default 3270 host.
	 * <p>
	 * When using a GUI session this connected to the session defined tn3270 host.
	 *  @param seconds	How many seconds to wait for a connection.
	 */
	public native void connect(int seconds);	
	
	/**
	 * Disconnect from tn3270 host.
	 */
	public native void disconnect();
	
	//
	// Getters
	//
	
	/**
	 * Get terminal contents at address.
	 *
	 * @param baddr address of string to get.
	 * @param len length of string to get.
	 *
	 * @return String at address with length chars.
	 *
	 */
	public native String getText(int baddr, int len);

	/**
	 * Get terminal contents at position.
	 *
	 * @param row row of the string start.
	 * @param col column of the string start.
	 * @param len length of string to get.
	 *
	 * @return String at position with length chars.
	 *
	 */
	public native String getText(int row, int col, int len);

	/**
	 * Get all terminal contents.
	 *
	 * @return The terminal contents.
	 */
	public String getText() {
		return getText(0,-1);
	}
		
	/**
	 * Get the current program message id.
	 * <p>
	 * <table>
	 * <tr><th>Value</th><th>Lib3270 name</th><th>Description</th></tr>
     * <tr><td>0</td><td>LIB3270_MESSAGE_NONE</td><td>No message</td></tr>
     * <tr><td>1</td><td>LIB3270_MESSAGE_SYSWAIT</td><td></td></tr>
     * <tr><td>2</td><td>LIB3270_MESSAGE_TWAIT</td><td></td></tr>
     * <tr><td>3</td><td>LIB3270_MESSAGE_CONNECTED</td><td>Connected to host</td></tr>
     * <tr><td>4</td><td>LIB3270_MESSAGE_DISCONNECTED</td><td>Disconnected from host</td></tr>
     * <tr><td>5</td><td>LIB3270_MESSAGE_AWAITING_FIRST</td><td></td></tr>
     * <tr><td>6</td><td>LIB3270_MESSAGE_MINUS</td><td></td></tr>
     * <tr><td>7</td><td>LIB3270_MESSAGE_PROTECTED</td><td></td></tr>
     * <tr><td>8</td><td>LIB3270_MESSAGE_NUMERIC</td><td></td></tr>
     * <tr><td>9</td><td>LIB3270_MESSAGE_OVERFLOW</td><td></td></tr>
     * <tr><td>10</td><td>LIB3270_MESSAGE_INHIBIT</td><td></td></tr>
     * <tr><td>11</td><td>LIB3270_MESSAGE_KYBDLOCK</td><td>Keyboard is locked</td></tr>
     * <tr><td>12</td><td>LIB3270_MESSAGE_X</td><td></td></tr> 
     * <tr><td>13</td><td>LIB3270_MESSAGE_RESOLVING</td><td>Resolving hostname (running DNS query)</td></tr>
     * <tr><td>14</td><td>LIB3270_MESSAGE_CONNECTING</td><td>Connecting to host</td></tr> 
	 * </table>
	 * @return The ProgramMessage value.
	 */
	public native int getProgramMessage();

	public native int getConnectionState();

	/**
	 * Get SSL state.
	 * <p>
	 * <table>
	 * <tr><th>Value</th><th>State</th></tr>
	 * <tr><td>0</td><td>Unsafe</td></tr>
	 * <tr><td>1</td><td>Valid CA</td></tr>
	 * <tr><td>2</td><td>Invalid CA or self-signed</td></tr>
	 * <tr><td>3</td><td>Negotiating</td></tr>
	 * <tr><td>4</td><td>Undefined</td></tr>
	 * </table>
	 * @return State of SSL connection.
	 */
	public native int getSSLState();


	/**
	 * Get bitmask with keyboard lock state.
	 * <p>
	 * <table>
	 * <tr><th>Value</th><th>Lib3270 id</th><th>Description</th></tr>
	 * <tr><td>0x0000</td><td>LIB3270_KL_UNLOCKED</td><td>Keyboard is unlocked.</td></tr>
	 * <tr><td>0x0001</td><td>LIB3270_KL_OERR_PROTECTED</td><td></td></tr>
	 * <tr><td>0x0002</td><td>LIB3270_KL_OERR_NUMERIC</td><td></td></tr>
	 * <tr><td>0x0003</td><td>LIB3270_KL_OERR_OVERFLOW</td><td></td></tr>
	 * <tr><td>0x0004</td><td>LIB3270_KL_OERR_DBCS</td><td></td></tr>
	 * <tr><td>0x0010</td><td>LIB3270_KL_NOT_CONNECTED</td><td>Not connected to host.</td></tr>
	 * <tr><td>0x0020</td><td>LIB3270_KL_AWAITING_FIRST</td><td></td></tr>
	 * <tr><td>0x0040</td><td>LIB3270_KL_OIA_TWAIT</td><td></td></tr>
	 * <tr><td>0x0080</td><td>LIB3270_KL_OIA_LOCKED</td><td></td></tr>
	 * <tr><td>0x0100</td><td>LIB3270_KL_DEFERRED_UNLOCK</td><td></td></tr>
	 * <tr><td>0x0200</td><td>LIB3270_KL_ENTER_INHIBIT</td><td></td></tr>
	 * <tr><td>0x0400</td><td>LIB3270_KL_SCROLLED</td><td></td></tr>
	 * <tr><td>0x0800</td><td>LIB3270_KL_OIA_MINUS</td><td></td></tr>
	 * </table>
     * @return The value of keyboard lock state.
     */
	public native int getKeyboardLockState();

	public native int getWidth();

	public native int getHeight();

	public native int getLength();

	public native int getCursorAddress();

	public native CursorPosition getCursorPosition();
	
	public native boolean contains(String chars);
	
	public native int find(String chars, int pos);
	
	public int find(String chars) {
		return find(chars,0);
	}

	/**
	 * Get connected state.
	 * @param timeout Time to wait for state (0 = no wait).
	 * @return true if the terminal is connected.
	 */
	public native boolean getConnected(int timeout);

	public boolean getConnected() {
		return getReady(0);
	}
	
	/**
	 * Get 'readyness' of terminal.
	 * @param timeout Time to wait for 'ready' state (0 = no wait).
	 * @return true if the terminal is ready.
	 */
	public native boolean getReady(int timeout);	

	public boolean getReady() {
		return getReady(0);
	}

	/** Get the lib3270 version string. */
	public native String getLib3270Version();

	/** Get the lib3270 revision string. */
	public native String getLib3270Revision();

	/** Get the LU name associated with the session, if there is one. */
	public native String getAssociatedLUName();

	public native String getURL();
	
	//
	// toString variations
	//
	
	public String toString(int baddr, int len) {
		return getText(baddr,len);
	}
	
	public String toString(int row, int col, int len) {
		return getText(row,col,len);
	}

	public String toString() {
		return getText(0,-1);
	}

	//
	// Setters
	//
	public native void setURL(String url);
	public native void setUnlockDelay(int delay);
	public native void setTimeout(int timeout);
	public native void setLockOnOperatorError(boolean lock);
	public native int setCursorPosition(int addr);
	public native int setCursorPosition(int row, int col);
	public native void setCharSet(String charset);
	public native void setProperty(String name, int value);
	public native void setProperty(String name, boolean value);
	public native void setProperty(String name, String value);

	public int setCursorPosition(CursorPosition position) {
		return setCursorPosition(position.row,position.col);
	}

	//
	// Actions
	//

	/**
	 * @brief Input string parsing control char.
     * @return The keyboard lock state.
     */
	public native int input(String str, char control);

	/**
	 * Input string parsing default control char (@).
     * <table>
	 * <tr><td>Insert string parsing the action codes prefixed with the defined control character.
	 * <tr><th>Value</th><th>Action</th><th>Description</th></tr>
	 * <tr><td>@@P</td><td></td><td>Print the screen contents (if available)</td></tr>
	 * <tr><td>@@@@</td><td></td><td>Input the @@ char.</td></tr>
	 * <tr><td>@@E</td><td>ENTER</td><td></td></tr>
	 * <tr><td>@@F</td><td>ERASE_EOF</td><td></td></tr>
	 * <tr><td>@@1</td><td>PF1</td><td>Send the PF1 key.</td></tr>
	 * <tr><td>@@2</td><td>PF2</td><td>Send the PF2 key.</td></tr>
	 * <tr><td>@@3</td><td>PF3</td><td>Send the PF3 key.</td></tr>
	 * <tr><td>@@4</td><td>PF4</td><td>Send the PF4 key.</td></tr>
	 * <tr><td>@@5</td><td>PF5</td><td>Send the PF5 key.</td></tr>
	 * <tr><td>@@6</td><td>PF6</td><td>Send the PF6 key.</td></tr>
	 * <tr><td>@@7</td><td>PF7</td><td>Send the PF7 key.</td></tr>
	 * <tr><td>@@8</td><td>PF8</td><td>Send the PF8 key.</td></tr>
	 * <tr><td>@@9</td><td>PF9</td><td>Send the PF9 key.</td></tr>
	 * <tr><td>@@a</td><td>PF10</td><td>Send the PF10 key.</td></tr>
	 * <tr><td>@@b</td><td>PF11</td><td>Send the PF11 key.</td></tr>
	 * <tr><td>@@c</td><td>PF12</td><td>Send the PF12 key.</td></tr>
	 * <tr><td>@@d</td><td>PF13</td><td>Send the PF13 key.</td></tr>
	 * <tr><td>@@e</td><td>PF14</td><td>Send the PF14 key.</td></tr>
	 * <tr><td>@@f</td><td>PF15</td><td>Send the PF15 key.</td></tr>
	 * <tr><td>@@g</td><td>PF16</td><td>Send the PF16 key.</td></tr>
	 * <tr><td>@@h</td><td>PF17</td><td>Send the PF17 key.</td></tr>
	 * <tr><td>@@u</td><td>PF18</td><td>Send the PF18 key.</td></tr>
	 * <tr><td>@@j</td><td>PF19</td><td>Send the PF19 key.</td></tr>
	 * <tr><td>@@k</td><td>PF20</td><td>Send the PF20 key.</td></tr>
	 * <tr><td>@@l</td><td>PF21</td><td>Send the PF21 key.</td></tr>
	 * <tr><td>@@m</td><td>PF22</td><td>Send the PF22 key.</td></tr>
	 * <tr><td>@@n</td><td>PF23</td><td>Send the PF23 key.</td></tr>
	 * <tr><td>@@o</td><td>PF24</td><td>Send the PF24 key.</td></tr>
	 * <tr><td>@@x</td><td>PA1</td><td>Send the PA1 key.</td></tr>
	 * <tr><td>@@y</td><td>PA2</td><td>Send the PA2 key.</td></tr>
	 * <tr><td>@@z</td><td>PA3</td><td>Send the PA3 key.</td></tr>
	 * <tr><td>@@D</td><td>CHAR_DELETE</td><td></td></tr>
	 * <tr><td>@@N</td><td>NEWLINE</td><td></td></tr>
	 * <tr><td>@@C</td><td>CLEAR</td><td></td></tr>
	 * <tr><td>@@R</td><td>KYBD_RESET</td><td></td></tr>
     * </table>
     * @return The keyboard lock state.
     */
	public int input(String str) {
		return input(str,'@');
	}

	public native void activate(String action);
	public native boolean activatable(String action);

	/**
	 * Send a pfkey to host.
	 *
	 * @param key PFkey number.
	 *
	 */
	public native void pfkey(int value);

	/**
	 * Send a pakey to host.
	 *
	 * @param key PAkey number.
	 *
	 */
	public native void pakey(int value);

	public native void enter();
	public native void erase();
	public native void erase_eol();
	public native void erase_input();
	
	/**
	 * Erase from cursor position until the end of the field.
	 */	
	public native void erase_eof();
	
	//
	// Wait
	//
	
	/**
	 * Wait for an specified amount of time.
	 * <p>
	 * Wait for the specified time keeping the main loop active.
	 *
	 * @param seconds Number of seconds to wait.
	 *
	 */
	public native void sleep(int seconds);	

	public native void wait(String text, int seconds);

	/**
	 * Wait for terminal negociation.
	 * <p>
	 * Wait on a loop until the terminal contents are
	 * ready for reading.
	 *
	 * @param seconds Maximum time (in seconds) to wait for.
	 *
	 */
	public native void wait(int seconds);

	/**
	 * Wait for text at defined address.
	 *
	 * @param baddr address of string.
	 * @param text	String to compare.
	 * @param seconds Maximum time (in seconds) to wait for.
	 *
	 */
	public native void wait(int baddr, String text, int seconds);

	/**
	 * Wait for text at defined row,col
	 *
	 * @param row	Row for text to compare.
	 * @param col	Column for text to compare.
	 * @param text	String to compare.
	 * @param seconds Maximum time (in seconds) to wait for.
	 *
	 */
	public native void wait(int row, int col, String text, int seconds);
		
	//
	// Legacy
	//
	

};

