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
package com.liferay.faces.bridge.internal;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.portlet.BaseURL;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.bridge.config.BridgeConfig;


/**
 * @author  Neil Griffin
 */
public class BridgeURLRedirectImpl extends BridgeURLBase {

	public BridgeURLRedirectImpl(FacesContext facesContext, String uri, String contextPath, String namespace,
		Map<String, List<String>> redirectParameters, BridgeConfig bridgeConfig) throws URISyntaxException {

		super(uri, contextPath, namespace, null, bridgeConfig);

		if (isJSF2PartialRequest(facesContext)) {
			bridgeURI.setParameter("_bridgeAjaxRedirect", "true");
		}

		if (redirectParameters != null) {

			Map<String, String[]> parameterMap = getParameterMap();
			Set<Entry<String, List<String>>> entrySet = redirectParameters.entrySet();

			for (Entry<String, List<String>> mapEntry : entrySet) {

				String key = mapEntry.getKey();
				String[] valueArray = null;
				List<String> valueList = mapEntry.getValue();

				if (valueList != null) {
					valueArray = valueList.toArray(new String[valueList.size()]);
				}

				parameterMap.put(key, valueArray);
			}
		}
	}

	// Java 1.6+ @Override
	public BaseURL toBaseURL() throws MalformedURLException {

		BaseURL baseURL;

		// If this is executing during the ACTION_PHASE of the portlet lifecycle, then
		if (BridgeUtil.getPortletRequestPhase() == Bridge.PortletPhase.ACTION_PHASE) {

			// TCK NOTE: The only use-case in which the TCK will invoke this method is
			// TestPage039-requestNoScopeOnRedirectTest. During the test, this method will be called when a <redirect/>
			// is encountered in a navigation-rule during the ACTION_PHASE of the portlet lifecycle. When this happens,
			// the navigation-handler will attempt to call ViewHandler#getResourceURL(String). The Mojarra
			// MultiViewHandler.getResourceURL(String) method is implemented in such a way that it calls
			// ExternalContext.encodeActionURL(ExternalContext.encodeResourceURL(url)). The return value of those calls
			// will ultimately be passed to the ExternalContext.redirect(String) method. For this reason, need to return
			// a simple string-based representation of the URL.
			baseURL = new BaseURLNonEncodedImpl(bridgeURI.toString(), getParameterMap());
		}

		// Otherwise,
		else {

			// So far, under all circumstances it seems appropriate to return a simple string-based representation of
			// the URL. This is the same code as above but keep it this way for now for TCK documentation purposes.
			baseURL = new BaseURLNonEncodedImpl(bridgeURI.toString(), getParameterMap());
		}

		return baseURL;
	}
}