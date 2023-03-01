/*
 * "Software pw3270, desenvolvido com base nos códigos fontes do WC3270  e X3270
 * (Paul Mattes Paul.Mattes@usa.net), de emulação de terminal 3270 para acesso a
 * aplicativos mainframe. Registro no INPI sob o nome G3270.
 *
 * Copyright (C) <2023> <Banco do Brasil S.A.>
 *
 * Este programa é software livre. Você pode redistribuí-lo e/ou modificá-lo sob
 * os termos da GPL v.2 - Licença Pública Geral  GNU,  conforme  publicado  pela
 * Free Software Foundation.
 *
 * Este programa é distribuído na expectativa de  ser  útil,  mas  SEM  QUALQUER
 * GARANTIA; sem mesmo a garantia implícita de COMERCIALIZAÇÃO ou  de  ADEQUAÇÃO
 * A QUALQUER PROPÓSITO EM PARTICULAR. Consulte a Licença Pública Geral GNU para
 * obter mais detalhes.
 *
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este
 * programa;  se  não, escreva para a Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA, 02111-1307, USA
 *
 * Este programa está nomeado como terminal.java e possui - linhas de código.
 *
 * Contatos:
 *
 * perry.werneck@gmail.com	(Alexandre Perry de Souza Werneck)
 * erico.mendonca@gmail.com	(Erico Mascarenhas Mendonça)
 *
 */

package br.app.pw3270;

public class Terminal {

	static {
        System.loadLibrary("jni3270");
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
	
	/// @brief Get the id of the current program message.
	/// @return The ProgramMessage value.
	public native int get_program_message();
	
	public native int get_connection_state();

	public native int get_get_ssl_state();
	
	public native int get_keyboard_lock_state();
    
	public native int get_screen_width();

	public native int get_screen_height();
	
	public native int get_screen_length();
	
	public native int get_cursor_address();
	
	public class CursorPosition {

		public int row = 0;
		public int col = 0;
		
		CursorPosition(int r, int c) {
			row = r;
			col = c;
		}

	}

	public native CursorPosition get_cursor_position();
	
	/// @brief Get the lib3270 version string.
	public native String get_lib3270_version();

	/// @brief Get the lib3270 revision string.
	public native String get_lib3270_revision();
	
	/// @brief Get the LU name associated with the session, if there is one.
	public native String get_associated_lu_name();
	    
	public native String get_host_url();
	 	    
};

