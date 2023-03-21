/*
 * Copyright 2023 David L. Whitehurst
 *
 * Licensed under the Apache License, Version 2.0
 * (the "License"); You may not use this file except
 * in compliance with the License. You may obtain a
 * copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *
 */

package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    /**
     * The functions listen at endpoint "/api/v1/<function>". 
     */

    /**
     * This function provides the implementation of:
     *  - GET /api/v1/states
     *  - POST /api/v1/states
     *  - DELETE /api/v1/states
     * 
     * @param request
     * @param context
     * @return
     */
    @FunctionName("handleStates")
    public HttpResponseMessage handleStates(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE},
                route = "states",
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<State>> request,
            final ExecutionContext context) {

                context.getLogger().info("Request body is: " + request.getBody().orElse(null));

                if (request.getHttpMethod().equals(HttpMethod.POST)) {
                // Check request body
                    if (!request.getBody().isPresent()) {
                        return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                            .body("Application/JSON body not found.")
                            .build();
                    } 
                    else {
                        // Produce response body
                        final State body = request.getBody().get();
                        return request.createResponseBuilder(HttpStatus.OK)
                            .header("Content-Type", "application/json")
                            .body(body)
                            .build();
                            // need to discuss status code and return e.g. 201 - Created
                    }
                } else if (request.getHttpMethod().equals(HttpMethod.DELETE)) {
                    // DELETE ALL (*** WARNING ***)
                    return request.createResponseBuilder(HttpStatus.NO_CONTENT).build(); // if body sent, will fail
                } else {
                    return request.createResponseBuilder(HttpStatus.OK).body("STATUS: 200 (Success), All states returned.").build();
                }
            }
    /**
     * This function provides the implementation of:
     *  - GET /api/v1/states/{stateId}
     *  - DELETE /api/v1/states/{stateId}
     *  - PUT /api/v1/states/{stateId}
     * 
     * @param request
     * @param context
     * @return
     */
    @FunctionName("getStateById")
    public HttpResponseMessage getStateById(
        @HttpTrigger(
            name = "req",
            methods = {HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT},
            route = "states/{stateId}/{anything=EMPTY}",
            authLevel = AuthorizationLevel.ANONYMOUS)
        HttpRequestMessage<Optional<String>> request,
            @BindingName("stateId") String id,
            @BindingName("anything") String anything,
            final ExecutionContext context) {

                context.getLogger().info("URL parameters are: " + id);

                if (request.getHttpMethod().equals(HttpMethod.GET)) {
                    // GET
                    final String name = "New York";
                    final String code = "NY";
    
                    final String jsonDocument = "{\"id\":\"" + id + "\", " + 
                    "\"name\": \"" + name + "\", " +
                    "\"code\": \"" + code + "\"}";
    
                    return request.createResponseBuilder(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(jsonDocument)
                        .build();
                    
                } else if (request.getHttpMethod().equals(HttpMethod.DELETE)) {
                    // DELETE
                    return request.createResponseBuilder(HttpStatus.NO_CONTENT).build(); // if body sent, will fail

                } else if (request.getHttpMethod().equals(HttpMethod.PUT)) {
                    // PUT
                    return request.createResponseBuilder(HttpStatus.OK).body("STATUS: 200 (Success), State updated/replaced.").build();

                } else {
                    // ??? something is wrong
                    return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("STATUS: 400 (Bad Request)").build();
                }

    }    
        
    /**
     * This function is currently used for a single testing example. More effort is needed to provide coverage and
     * automated testing. Address this when time is available.
     * 
     *  e.g call > curl -i {your host}/api/v1/dummy
     * 
     * @param request
     * @param context
     * @return
     */
    @FunctionName("dummy")
    public HttpResponseMessage dummy(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");
        return request.createResponseBuilder(HttpStatus.OK).body("Version 1.0.3").build();
    }    
}
