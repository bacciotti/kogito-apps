/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
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

package org.kie.kogito.jobs.service.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.kie.kogito.jobs.service.repository.ReactiveJobRepository;
import org.kie.kogito.jobs.service.scheduler.impl.TimerDelegateJobScheduler;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class KafkaReactiveMessagingEventConsumer extends MessagingConsumer {

    private static final String KOGITO_JOB_SERVICE_JOB_REQUEST_EVENTS = "kogito-job-service-job-request-events";

    @Inject
    public KafkaReactiveMessagingEventConsumer(TimerDelegateJobScheduler scheduler,
            ReactiveJobRepository jobRepository,
            ObjectMapper objectMapper) {
        super(scheduler, jobRepository, objectMapper);
    }

    @Incoming(KOGITO_JOB_SERVICE_JOB_REQUEST_EVENTS)
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    @Retry(delay = 500, maxRetries = 4)
    @Override
    public Uni<Void> onKogitoServiceRequest(Message<CloudEvent> message) {
        return super.onKogitoServiceRequest(message);
    }
}
