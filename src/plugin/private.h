/*
 * "Software pw3270, desenvolvido com base nos códigos fontes do WC3270  e X3270
 * (Paul Mattes Paul.Mattes@usa.net), de emulação de terminal 3270 para acesso a
 * aplicativos mainframe. Registro no INPI sob o nome G3270.
 *
 * Copyright (C) <2008> <Banco do Brasil S.A.>
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
 * Este programa está nomeado como private.h e possui - linhas de código.
 *
 * Contatos:
 *
 * perry.werneck@gmail.com	(Alexandre Perry de Souza Werneck)
 * erico.mendonca@gmail.com	(Erico Mascarenhas Mendonça)
 *
 * Referências:
 *
 * http://thebreakfastpost.com/2012/01/26/wrapping-a-c-library-with-jni-part-2/
 *
 */
#ifndef PRIVATE_H_INCLUDED

	#define PRIVATE_H_INCLUDED

	#include <jni.h>

	#include <exception>

	#include <malloc.h>
	#include <libintl.h>

	#include <glib/gi18n.h>
	#include <gtk/gtk.h>

	G_GNUC_INTERNAL JavaVM   * jvm;
	G_GNUC_INTERNAL JNIEnv   * env;

	G_GNUC_INTERNAL bool load_jvm(GtkWidget *widget);
	G_GNUC_INTERNAL void call(GtkWidget *widget, const char *classname);
	G_GNUC_INTERNAL void lock();
	G_GNUC_INTERNAL void unlock();
	G_GNUC_INTERNAL bool trylock();
	G_GNUC_INTERNAL void failed(GtkWidget *widget, const char *msg, const char *format, ...);

	using namespace std;

#endif // PRIVATE_H_INCLUDED
