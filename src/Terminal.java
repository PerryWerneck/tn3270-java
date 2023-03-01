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
	public static native String get_version();
	
	/// @brief Get the current ipc3270 revision.
	/// @return String with the current ipc3270 revision.
	public static native String get_revision();
	
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
	public native int get_program_message();
	
	public native int get_connection_state();

	/// @brief Get SSL state.
	/// @return State of SSL connection (0 = Unsafe, 1 = Valid CA, 2 = Invalid CA or self-signed, 3 = Negotiating, 4 = Undefined)
	public native int get_get_ssl_state();
	
	public native int get_keyboard_lock_state();
    
	public native int get_screen_width();

	public native int get_screen_height();
	
	public native int get_screen_length();
	
	public native int get_cursor_address();
	
	public native CursorPosition get_cursor_position();
	
	/// @brief Get the lib3270 version string.
	public native String get_lib3270_version();

	/// @brief Get the lib3270 revision string.
	public native String get_lib3270_revision();
	
	/// @brief Get the LU name associated with the session, if there is one.
	public native String get_associated_lu_name();
	    
	public native String get_host_url();
	
	//
	// Setters
	//
	
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
	
	Action get_action(String name);
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

