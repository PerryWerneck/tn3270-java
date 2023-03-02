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

public class Terminal implements AutoCloseable {

	static {
        System.loadLibrary("jni3270");
    }

    //
    // Inner classes
    //
	public class CursorPosition {

		public int row = 0;
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

	public Terminal() {
		open();
	}

	public Terminal(String id) {
		open(id,"UTF-8");
	}

	/// @brief Get the current ipc3270 version.
	/// @return String with the current ipc3270 version.
	public static native String getVersion();

	/// @brief Get the current ipc3270 revision.
	/// @return String with the current ipc3270 revision.
	public static native String getRevision();

	/// @brief Open headless tn3270 session.
	public native void open(String id, String charset);

	/// @brief Open headless tn3270 session with default charset.
	public void open() {
		open("","UTF-8");
	}

	/// @brief Open tn3270 session with default charset.
	public void open(String id) {
		open(id,"UTF-8");
	}

	/// @brief Close tn3270 session.
	public native void close();
	
	//
	// Connection
	//
	
	///
	/// @brief Connect to 3270 host.
	/// <p>
	/// Connect to the 3270 host
	/// <p>
	/// URI formats:
	/// <ul>
	/// <li>tn3270://[HOSTNAME]:[HOSTPORT] for non SSL connections.</li>
	/// <li>tn3270s://[HOSTNAME]:[HOSTPORT] for ssl connection.</li>
	/// </ul>
	///
	/// @param host		Host URI.
	/// @param seconds	How many seconds to wait for a connection.
	public native void connect(String url, int seconds);	

	public native void connect(int seconds);	
	
	public native void disconnect();
	
	//
	// Getters
	//
	public native String getText(int baddr, int len);
	public native String getText(int row, int col, int len);

	public String getText() {
		return getText(0,-1);
	}
		
	/// @brief Get the id of the current program message.
	/// @return The ProgramMessage value.
	public native int getProgramMessage();

	public native int getConnectionState();

	/// @brief Get SSL state.
	/// @return State of SSL connection (0 = Unsafe, 1 = Valid CA, 2 = Invalid CA or self-signed, 3 = Negotiating, 4 = Undefined)
	public native int getSSLState();

	public native int getKeyboardLockState();

	public native int getWidth();

	public native int getHeight();

	public native int getLength();

	public native int getCursorAddress();

	public native CursorPosition getCursorPosition();
	
	/// @brief Get connected state.
	/// @param timeout Time to wait for state (0 = no wait).
	/// @return true if the terminal is connected.
	public native boolean getConnected(int timeout);

	public boolean getConnected() {
		return getReady(0);
	}
	
	/// @brief Get 'readyness' of terminal.
	/// @param timeout Time to wait for state (0 = no wait).
	/// @return true if the terminal is ready.
	public native boolean getReady(int timeout);	

	public boolean getReady() {
		return getReady(0);
	}

	/// @brief Get the lib3270 version string.
	public native String getLib3270Version();

	/// @brief Get the lib3270 revision string.
	public native String getLib3270Revision();

	/// @brief Get the LU name associated with the session, if there is one.
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

	/// @brief Input string parsing control char.
	/// @return The keyboard lock state.
	public native int input(String str, char control);

	/// @brief Input string parsing control char.
	///
	/// Insert string parsing the action codes prefixed with the defined control character.
	///
	/// Value | Action      | Description                                                |
	/// :----:|:------------|:-----------------------------------------------------------|
	///  @@P  | -           | Print the screen contents (if available)                   |
	///  @@@@ | -           | Input the @@ char.                                         |
	///  @@E  | ENTER       | -                                                          |
	///  @@F  | ERASE_EOF   | -                                                          |
	///  @@1  | PF1         | Send the PF1 key.                                          |
	///  @@2  | PF2         | Send the PF2 key.                                          |
	///  @@3  | PF3         | Send the PF3 key.                                          |
	///  @@4  | PF4         | Send the PF4 key.                                          |
	///  @@5  | PF5         | Send the PF5 key.                                          |
	///  @@6  | PF6         | Send the PF6 key.                                          |
	///  @@7  | PF7         | Send the PF7 key.                                          |
	///  @@8  | PF8         | Send the PF8 key.                                          |
	///  @@9  | PF9         | Send the PF9 key.                                          |
	///  @@a  | PF10        | Send the PF10 key.                                         |
	///  @@b  | PF11        | Send the PF11 key.                                         |
	///  @@c  | PF12        | Send the PF12 key.                                         |
	///  @@d  | PF13        | Send the PF13 key.                                         |
	///  @@e  | PF14        | Send the PF14 key.                                         |
	///  @@f  | PF15        | Send the PF15 key.                                         |
	///  @@g  | PF16        | Send the PF16 key.                                         |
	///  @@h  | PF17        | Send the PF17 key.                                         |
	///  @@u  | PF18        | Send the PF18 key.                                         |
	///  @@j  | PF19        | Send the PF19 key.                                         |
	///  @@k  | PF20        | Send the PF20 key.                                         |
	///  @@l  | PF21        | Send the PF21 key.                                         |
	///  @@m  | PF22        | Send the PF22 key.                                         |
	///  @@n  | PF23        | Send the PF23 key.                                         |
	///  @@o  | PF24        | Send the PF24 key.                                         |
	///  @@x  | PA1         | Send the PA1 key.                                          |
	///  @@y  | PA2         | Send the PA2 key.                                          |
	///  @@z  | PA3         | Send the PA3 key.                                          |
	///  @@D  | CHAR_DELETE |                                                            |
	///  @@N  | NEWLINE     |                                                            |
	///  @@C  | CLEAR       |                                                            |
	///  @@R  | KYBD_RESET  |                                                            |
	///  @@<  | BACKSPACE   |                                                            |
	/// :----:|:------------|:-----------------------------------------------------------|
	///
	/// @return The keyboard lock state.
	///
	public int input(String str) {
		return input(str,'@');
	}

	public native void activate(String action);
	public native boolean activatable(String action);

	public native void pfkey(int value);
	public native void pakey(int value);

	public native void enter();
	public native void erase();
	public native void erase_eol();
	public native void erase_input();
	public native void erase_eof();

};

