/*
 * Copyright 2012 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.springsecurity.oauth

import grails.converters.JSON

/**
 * @author <a href='mailto:cazacugmihai@gmail.com'>Mihai Cazacu</a>
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
class YahooSpringSecurityOAuthService {

    def oauthService

    def createAuthToken(accessToken) {
        def response = oauthService.getYahooResource(accessToken, 'http://social.yahooapis.com/v1/me/guid?format=json')
        def responseAsJson = JSON.parse(response.body)
        def guid = responseAsJson.guid.value

        response = oauthService.getYahooResource(accessToken, "http://social.yahooapis.com/v1/user/${guid}/profile?format=json")
        def profile = JSON.parse(response.body).profile

        return new YahooOAuthToken(accessToken, profile)
    }

}

