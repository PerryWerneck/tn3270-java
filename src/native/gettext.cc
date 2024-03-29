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

 #include <config.h>
 #include <jni.h>
 #include <jni3270.h>
 #include <lib3270/ipc/session.h>
 #include <private/br_app_pw3270_Terminal.h>
 #include <stdexcept>

 using namespace std;

JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_getText__II(JNIEnv *env, jobject obj, jint baddr, jint len) {

	return call(env,obj,[baddr,len](TN3270::Session &session){
		return session.toString((int) baddr, (int) len);
	});

}

JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_getText__III(JNIEnv *env, jobject obj, jint row, jint col, jint len) {

	return call(env,obj,[row,col,len](TN3270::Session &session){
		return session.toString((unsigned short) row, (unsigned short) col, (int) len);
	});

}
