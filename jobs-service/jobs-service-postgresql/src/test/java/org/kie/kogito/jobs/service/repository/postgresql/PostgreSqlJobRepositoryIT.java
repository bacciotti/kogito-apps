/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

package org.kie.kogito.jobs.service.repository.postgresql;

import java.time.Duration;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.kie.kogito.jobs.service.repository.ReactiveJobRepository;
import org.kie.kogito.jobs.service.repository.impl.BaseJobRepositoryTest;
import org.kie.kogito.testcontainers.quarkus.PostgreSqlQuarkusTestResource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;

@QuarkusTest
@QuarkusTestResource(PostgreSqlQuarkusTestResource.class)
public class PostgreSqlJobRepositoryIT extends BaseJobRepositoryTest {

    @Inject
    PostgreSqlJobRepository tested;

    @Inject
    PgPool client;

    @BeforeEach
    public void setUp() throws Exception {
        client.query("DELETE FROM job_details")
                .execute()
                .await().atMost(Duration.ofSeconds(10L));
        client.query("DELETE FROM job_service_management")
                .execute()
                .await().atMost(Duration.ofSeconds(10L));
        super.setUp();
    }

    @Override
    public ReactiveJobRepository tested() {
        return tested;
    }
}
