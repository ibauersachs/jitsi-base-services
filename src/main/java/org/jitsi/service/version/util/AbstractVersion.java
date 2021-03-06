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
package org.jitsi.service.version.util;

import org.jitsi.service.version.*;

/**
 * Base class for {@link Version} implementation that uses major, minor and
 * nightly build id for versioning purposes.
 *
 * @author Emil Ivov
 * @author Pawel Domas
 */
public abstract class AbstractVersion
    implements Version
{
    /**
     * The version major field.
     */
    private int versionMajor;

    /**
     * The version minor field.
     */
    private int versionMinor;

    /**
     * The nightly build id field.
     */
    private String nightlyBuildID;

    /**
     * Creates version object with custom major, minor and nightly build id.
     *
     * @param majorVersion the major version to use.
     * @param minorVersion the minor version to use.
     * @param nightlyBuildID the nightly build id value for new version object.
     */
    protected AbstractVersion(int majorVersion,
                              int minorVersion,
                              String nightlyBuildID)
    {
        this.versionMajor = majorVersion;
        this.versionMinor = minorVersion;
        this.nightlyBuildID = nightlyBuildID;
    }

    /**
     * Returns the version major of the current version. In an
     * example 2.3.1 version string 2 is the version major. The version major
     * number changes when a relatively extensive set of new features and
     * possibly rearchitecturing have been applied.
     *
     * @return the version major String.
     */
    public int getVersionMajor()
    {
        return versionMajor;
    }

    /**
     * Returns the version minor of the current version. In an
     * example 2.3.1 version string 3 is the version minor. The version minor
     * number changes after adding enhancements and possibly new features to a
     * given version.
     *
     * @return the version minor integer.
     */
    public int getVersionMinor()
    {
        return versionMinor;
    }

    /**
     * If this is a nightly build, returns the build identifies (e.g.
     * nightly-2007.12.07-06.45.17). If this is not a nightly build Jitsi
     * version, the method returns null.
     *
     * @return a String containing a nightly build identifier or null if this is
     * a release version and therefore not a nightly build
     */
    public String getNightlyBuildID()
    {
        if(!isNightly())
        {
            return null;
        }

        return nightlyBuildID;
    }

    /**
     * Compares another {@code Version} object to this one and returns a
     * negative, zero or a positive integer if this version instance represents
     * respectively an earlier, same, or later version as the one indicated
     * by the {@code version} parameter.
     *
     * @param version the {@code Version} instance that we'd like to compare
     * to this one.
     *
     * @return a negative integer, zero, or a positive integer as this object
     * represents a version that is earlier, same, or more recent than the one
     * referenced by the {@code version} parameter.
     */
    @Override
    public int compareTo(Version version)
    {
        if(version == null)
        {
            return -1;
        }

        if(getVersionMajor() != version.getVersionMajor())
        {
            return getVersionMajor() - version.getVersionMajor();
        }

        if(getVersionMinor() != version.getVersionMinor())
        {
            return getVersionMinor() - version.getVersionMinor();
        }

        try
        {
            return compareNightlyBuildIDByComponents(
                getNightlyBuildID(), version.getNightlyBuildID());
        }
        catch(Throwable th)
        {
            // if parsing fails will continue with lexicographically compare
        }

        return getNightlyBuildID().compareTo(version.getNightlyBuildID());
    }

    /**
     * As normal {@code nightly.build.id} is in the form of
     * {@code &lt;build-num&gt;} or {@code &lt;build-num&gt;.&lt;revision&gt;}
     * we will first try to compare them by splitting the id in components
     * and compare them one by one asnumbers
     * @param v1 the first version to compare
     * @param v2 the second version to compare
     * @return a negative integer, zero, or a positive integer as the first
     * parameter {@code v1} represents a version that is earlier, same,
     * or more recent than the one referenced by the {@code v2} parameter.
     */
    private static int compareNightlyBuildIDByComponents(String v1, String v2)
    {
        String[] s1 = v1.split("\\.");
        String[] s2 = v2.split("\\.");

        int len = Math.max(s1.length, s2.length);
        for(int i = 0; i < len; i++)
        {
            int n1 = 0;
            int n2 = 0;

            if(i < s1.length)
            {
                n1 = Integer.parseInt(s1[i]);
            }

            if(i < s2.length)
            {
                n2 = Integer.parseInt(s2[i]);
            }

            if(n1 != n2)
            {
                return n1 - n2;
            }
        }

        // will happen if both versions have identical numbers in
        // their components (even if one of them is longer, has more components)
        return 0;
    }

    /**
     * Compares the {@code version} parameter to this version and returns true
     * if and only if both reference the same version and
     * false otherwise.
     *
     * @param version the version instance that we'd like to compare with this
     * one.
     * @return true if and only if the version param references the same
     * version as this {@link Version} instance and false otherwise.
     */
    @Override
    public boolean equals(Object version)
    {
        if (version == null)
        {
            return false;
        }

        //simply compare the version strings
        return toString().equals(version.toString());

    }

    /**
     * Returns a string representation of this {@link Version} instance in the
     * generic form.
     *
     * @return a string containing the complete version in the form
     * of {@code major.minor[-prerelease][.build]}.
     */
    @Override
    public String toString()
    {
        StringBuilder versionStringBuff = new StringBuilder();
        versionStringBuff.append(getVersionMajor());
        versionStringBuff.append(".");
        versionStringBuff.append(getVersionMinor());

        if(isPreRelease())
        {
            versionStringBuff.append("-");
            versionStringBuff.append(getPreReleaseID());
        }

        if(isNightly())
        {
            versionStringBuff.append(".");
            versionStringBuff.append(getNightlyBuildID());
        }

        return versionStringBuff.toString();
    }
}
