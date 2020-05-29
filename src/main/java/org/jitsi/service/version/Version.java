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
 * Contains version information of the Jitsi instance that we're currently
 * running.
 *
 * @author Emil Ivov
 * @author Lyubomir Marinov
 */
public interface Version extends Comparable<Version>
{
    /**
     * The name of the {@code System} property the value of which is equal to
     * the value of {@link #getApplicationName()}. Expected to be set by
     * implementers of the {@link VersionService} and {@link Version}
     * interfaces.
     */
    String PNAME_APPLICATION_NAME = "sip-communicator.application.name";

    /**
     * The name of the {@code System} property the value of which is equal to
     * the value of {@link #toString()}. Expected to be set by
     * implementers of the {@link VersionService} and {@link Version}
     * interfaces.
     */
    String PNAME_APPLICATION_VERSION = "sip-communicator.version";

    /**
     * Returns the version major of the current Jitsi version. In an
     * example 2.3.1 version string 2 is the version major. The version major
     * number changes when a relatively extensive set of new features and
     * possibly rearchitecturing have been applied.
     *
     * @return the version major integer.
     */
    int getVersionMajor();

    /**
     * Returns the version minor of the current version. In an
     * example 2.3.1 version string 3 is the version minor. The version minor
     * number changes after adding enhancements and possibly new features to a
     * given version.
     *
     * @return the version minor integer.
     */
    int getVersionMinor();

    /**
     * Indicates if this version corresponds to a nightly build
     * of a repository snapshot or to an official release.
     *
     * @return true if this is a build of a nightly repository snapshot and
     * false if this is an official release.
     */
    boolean isNightly();

    /**
     * If this is a nightly build, returns the build identifies (e.g.
     * nightly-2007.12.07-06.45.17). If this is not a nightly build
     * version, the method returns {@code null}.
     *
     * @return a String containing a nightly build identifier or {@code null}.
     */
    String getNightlyBuildID();

    /**
     * Indicates whether this version represents a prerelease (i.e. a
     * non-complete release like an alpha, beta or release candidate version).
     * @return true if this version represents a prerelease and false otherwise.
     */
    boolean isPreRelease();

    /**
     * Returns the version prerelease ID of the current version
     * and null if this version is not a prerelease. Version pre-release id-s
     * exist only for pre-release versions and are {@code null} otherwise.
     *
     * @return a String containing the version prerelease ID.
     */
    String getPreReleaseID();

    /**
     * Returns the name of the application that we're currently running.
     *
     * @return the name of the application that we're currently running.
     */
    String getApplicationName();

    /**
     * Returns a String representation of this {@link Version} instance.
     *
     * @return a major.minor[.build] String containing the complete
     * application version.
     */
    String toString();
}
