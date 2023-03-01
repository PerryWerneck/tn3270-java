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

public class Terminal {

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


	// TN3270 native handler.
	private long hSession;

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
		open("","");
	}

	/// @brief Open tn3270 session with default charset.
	public void open(String id) {
		open(id,"");
	}

	/// @brief Close tn3270 session.
	public native void close();

	//
	// Getters
	//

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

	/// @brief Get the lib3270 version string.
	public native String getLib3270Version();

	/// @brief Get the lib3270 revision string.
	public native String getLib3270Revision();

	/// @brief Get the LU name associated with the session, if there is one.
	public native String getAssociatedLUName();

	public native String getURL();

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

	//
	// Actions
	//

	/*
	public class Action {

		// Action native handler.
		private long hAction;

		public native bool activatable();
		public native void activate();

		public native String name();
		public native String description();
		public native String summary();

		Action(long hSession, String name);

	}

	Action getAction(String name);
	*/

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

