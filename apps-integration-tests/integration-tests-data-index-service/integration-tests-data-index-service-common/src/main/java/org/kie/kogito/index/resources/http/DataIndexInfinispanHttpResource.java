/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
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

package org.kie.kogito.index.resources.http;

import java.util.HashMap;
import java.util.Map;

import org.kie.kogito.index.testcontainers.DataIndexInfinispanContainer;
import org.kie.kogito.test.resources.TestResource;
import org.kie.kogito.testcontainers.KogitoInfinispanContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Network;

public class DataIndexInfinispanHttpResource implements TestResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataIndexInfinispanHttpResource.class);

    KogitoInfinispanContainer infinispan = new KogitoInfinispanContainer();
    DataIndexInfinispanContainer dataIndex = new DataIndexInfinispanContainer();
    Map<String, String> properties = new HashMap<>();

    @Override
    public String getResourceName() {
        return dataIndex.getResourceName();
    }

    @Override
    public void start() {
        LOGGER.debug("Start Infinispan Quarkus test resource");
        properties.clear();
        Network network = Network.newNetwork();
        infinispan.withNetwork(network);
        infinispan.withNetworkAliases("infinispan");
        infinispan.start();
        String infinispanURL = "localhost:" + infinispan.getMappedPort();
        properties.put("quarkus.infinispan-client.hosts", infinispanURL);
        dataIndex.addProtoFileFolder();
        dataIndex.withNetwork(network);
        dataIndex.setInfinispanURL("infinispan:11222");
        dataIndex.addEnv("QUARKUS_PROFILE", "http-events-support");
        dataIndex.start();
        LOGGER.debug("Data Index Service started");
    }

    @Override
    public void stop() {
        dataIndex.stop();
        infinispan.stop();
        LOGGER.debug("Stop Infinispan Quarkus test resource");
    }

    @Override
    public int getMappedPort() {
        return dataIndex.getMappedPort();
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
