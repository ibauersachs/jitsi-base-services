/*
 * Copyright @ 2015 Atlassian Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jitsi.service.version;

/**
 * The version service keeps track of the application version that we are
 * currently running.
 *
 * @author Emil Ivov
 */
public interface VersionService
{
    /**
     * Returns a {@link Version} object containing version details of the
     * version that we're currently running.
     *
     * @return a {@link Version} object containing version details of the
     * version that we're currently running.
     */
    Version getCurrentVersion();

    /**
     * Returns a Version instance corresponding to the {@code version} string.
     *
     * @param version a version string that we have obtained by calling a
     * {@link Version#toString()} method.
     *
     * @return the {@link Version} object corresponding to the {@code version}
     * string.
     */
    Version parseVersionString(String version);
}
