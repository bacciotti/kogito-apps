/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.kogito.jobs.service.resource;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.kie.kogito.jobs.service.management.ReleaseLeaderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Uni;

@Path("/management")
public class JobServiceManagementResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceManagementResource.class);

    @Inject
    Event<ReleaseLeaderEvent> releaseLeaderEventEvent;

    @POST
    @Path("/shutdown")
    public Uni<Response> shutdownHook() {
        return Uni.createFrom().voidItem()
                .onItem().invoke(i -> LOGGER.info("Job Service is shutting down"))
                .onItem().invoke(() -> releaseLeaderEventEvent.fire(new ReleaseLeaderEvent()))
                .onItem().transform(i -> Response.ok().build());
    }
}
