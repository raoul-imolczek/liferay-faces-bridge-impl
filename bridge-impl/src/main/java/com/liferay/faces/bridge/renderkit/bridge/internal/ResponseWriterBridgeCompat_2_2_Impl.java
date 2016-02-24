/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.bridge.renderkit.bridge.internal;

import java.io.IOException;

import javax.faces.context.ResponseWriter;


/**
 * @author  Neil Griffin
 */
public abstract class ResponseWriterBridgeCompat_2_2_Impl extends ResponseWriterBridgeCompat_2_0_Impl {

	// Protected Constants
	protected static final String CLIENT_WINDOW_PARAM = "javax.faces.ClientWindow";

	public ResponseWriterBridgeCompat_2_2_Impl(ResponseWriter wrappedResponseWriter) {
		super(wrappedResponseWriter);
	}

	public void writePreamble(String preamble) throws IOException {
		// No-op for portlets: http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1069
	}

	protected void writeClientWindowHiddenField() throws IOException {
		// No-op for JSF 2.1
	}
}