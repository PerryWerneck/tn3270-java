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
 #include <lib3270/ipc/action.h>
 #include <lib3270/ipc/session.h>
 #include <private/br_app_pw3270_Terminal_Action.h>
 #include <stdexcept>

 using namespace std;

 struct Handler {

	std::shared_ptr<TN3270::Action> hAction;

	Handler(TN3270::Session &session, const char *name) : hAction{session.ActionFactory(name)} {
	}

 };

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_00024Action_open(JNIEnv *env, jobject obj, jlong hSession, jstring name) {

	try {

		auto field =
			env->GetFieldID(
				env->GetObjectClass(obj),
				"hAction",
				"J"
			);

		Handler * handler =
			reinterpret_cast<Handler *>(
				env->GetLongField(
					obj,
					field
				)
			);

		if(handler) {
			throw runtime_error("Action is already open");
		}

		handler = new Handler(getSessionFromJLong(hSession),env->GetStringUTFChars(name,0));

		env->SetLongField(
			obj,
			field,
			reinterpret_cast<jlong>(handler)
		);

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error opening action");

	}

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_00024Action_close(JNIEnv *env, jobject obj) {

	try {

		auto field =
			env->GetFieldID(
				env->GetObjectClass(obj),
				"hAction",
				"J"
			);

		Handler * handler =
			reinterpret_cast<Handler *>(
				env->GetLongField(
					obj,
					field
				)
			);

		if(!handler) {
			return;
		}

		env->SetLongField(obj, field, 0);
		delete handler;

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error closing action");

	}

 }

 static TN3270::Action & getAction(JNIEnv *env, jobject obj) {

	Handler * handler =
		reinterpret_cast<Handler *>(
			env->GetLongField(
				obj,
				env->GetFieldID(
					env->GetObjectClass(obj),
					"hAction",
					"J"
				)
			)
		);

	if(!handler) {
		throw runtime_error("Action is not open");
	}

	return *handler->hAction;
 }

 JNIEXPORT jboolean JNICALL Java_br_app_pw3270_Terminal_00024Action_activatable(JNIEnv *env, jobject obj) {

	try {

		return getAction(env,obj).activatable();

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error");

	}

	return 0;

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_00024Action_activate(JNIEnv *env, jobject obj) {

	try {

		getAction(env,obj).activate();

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error");

	}

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_00024Action_name(JNIEnv *env, jobject obj) {

	try {

		return env->NewStringUTF(getAction(env,obj).name());

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error");

	}

	return jstring{};

 }


 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_00024Action_description(JNIEnv *env, jobject obj) {

	try {

		return env->NewStringUTF(getAction(env,obj).description());

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error");

	}

	return jstring{};

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_00024Action_summary(JNIEnv *env, jobject obj) {

	try {

		return env->NewStringUTF(getAction(env,obj).summary());

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error");

	}

	return jstring{};

 }
