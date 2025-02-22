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
package org.kie.kogito.job.http.recipient;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(prefix = "kogito", name = "job.recipient.http", phase = ConfigPhase.RUN_TIME)
public class JobHttpRecipientRuntimeConfiguration {

    /**
     * Default timeout to execute HTTP requests for the HttpRecipient when the Job's timeout is not configured.
     */
    @ConfigItem(name = "timeout-in-millis", defaultValue = "180000")
    long timeoutInMillis;

    /**
     * Max accepted timeout to execute HTTP requests for the HttpRecipient when the Job's timeout is configured.
     * Attempts to surpass this value will result in a validation error at Job creation time.
     */
    @ConfigItem(name = "max-timeout-in-millis", defaultValue = "300000")
    long maxTimeoutInMillis;
}
