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
package org.jitsi.service.configuration;

import java.beans.*;
import java.io.*;
import java.util.*;

/**
 * The configuration services provides a centralized approach of storing
 * persistent configuration data.
 *
 * @author Emil Ivov
 * @author Lyubomir Marinov
 * @author Dmitri Melnikov
 */
public interface ConfigurationService
{
    /**
     * The name of the property that indicates the name of the directory where
     * Jitsi is to store user specific data such as configuration
     * files, message and call history.
     */
    String PNAME_SC_HOME_DIR_NAME
        = "net.java.sip.communicator.SC_HOME_DIR_NAME";

    /**
     * The name of the property that indicates the location of the directory
     * where Jitsi is to store user specific data such as
     * configuration files, message and call history.
     */
    String PNAME_SC_HOME_DIR_LOCATION
        = "net.java.sip.communicator.SC_HOME_DIR_LOCATION";

    /**
     * The name of the property that indicates the location of the directory
     * where Jitsi is to store cached data.
     */
    String PNAME_SC_CACHE_DIR_LOCATION
        = "net.java.sip.communicator.SC_CACHE_DIR_LOCATION";

    /**
     * The name of the property that indicates the location of the directory
     * where Jitsi is to store cached data.
     */
    String PNAME_SC_LOG_DIR_LOCATION
        = "net.java.sip.communicator.SC_LOG_DIR_LOCATION";

    /**
     * The name of the boolean system property  which indicates whether the
     * configuration file is to be considered read-only. The default value is
     * {@code false} which means that the configuration file is considered
     * writable.
     */
    String PNAME_CONFIGURATION_FILE_IS_READ_ONLY
        = "net.java.sip.communicator.CONFIGURATION_FILE_IS_READ_ONLY";

    /**
     * The name of the system property that stores the name of the configuration
     * file.
     */
    String PNAME_CONFIGURATION_FILE_NAME
        = "net.java.sip.communicator.CONFIGURATION_FILE_NAME";

    /**
     * Sets the property with the specified name to the specified value. Calling
     * this method would first trigger a PropertyChangeEvent that will
     * be dispatched to all VetoableChangeListeners. In case no complaints
     * (PropertyVetoException) have been received, the property will be actually
     * changed and a PropertyChangeEvent will be dispatched.
     * <p>
     * @param propertyName the name of the property to change.
     * @param property the new value of the specified property.
     * @throws ConfigPropertyVetoException in case the changed has been refused
     * by at least one propertychange listener.
     */
    void setProperty(String propertyName, Object property);

    /**
     * Sets the property with the specified name to the specified value. Calling
     * this method would first trigger a PropertyChangeEvent that will
     * be dispatched to all VetoableChangeListeners. In case no complaints
     * (PropertyVetoException) have been received, the property will be actually
     * changed and a PropertyChangeEvent will be dispatched. This method also
     * allows the caller to specify whether or not the specified property is a
     * system one.
     * <p>
     * @param propertyName the name of the property to change.
     * @param property the new value of the specified property.
     * @param isSystem specifies whether or not the property being set is a
     * System property and should be resolved against the system property set.
     *
     * @throws ConfigPropertyVetoException in case the changed has been refused by
     * at least one propertychange listener.
     */
    void setProperty(String propertyName,
                     Object property,
                     boolean isSystem);

    /**
     * Sets a set of specific properties to specific values as a batch operation
     * meaning that first {@code VetoableChangeListener}s are asked to
     * approve the modifications of the specified properties to the specified
     * values, then the modifications are performed if no complaints have been
     * raised in the form of {@code PropertyVetoException} and finally
     * {@code PropertyChangeListener}s are notified about the changes of
     * each of the specified properties. The batch operations allows the
     * {@code ConfigurationService} implementations to optimize, for
     * example, the saving of the configuration which in this case can be
     * performed only once for the setting of multiple properties.
     *
     * @param properties a {@link Map} of property names to their new values to
     * be set.
     * @throws ConfigPropertyVetoException if a change in at least one of the
     * properties has been refused by at least one of the {@link
     * VetoableChangeListener}s.
     */
    void setProperties(Map<String, Object> properties);

    /**
     * Returns the value of the property with the specified name or null if no
     * such property exists.
     * @param propertyName the name of the property that is being queried.
     * @return the value of the property with the specified name.
     */
    Object getProperty(String propertyName);

    /**
     * Removes the property with the specified name. Calling
     * this method would first trigger a PropertyChangeEvent that will
     * be dispatched to all VetoableChangeListeners. In case no complaints
     * (PropertyVetoException) have been received, the property will be actually
     * changed and a PropertyChangeEvent will be dispatched.
     * All properties with prefix propertyName will also be removed.
     * <p>
     * @param propertyName the name of the property to change.
     * @throws ConfigPropertyVetoException in case the changed has been refused by
     * at least one propertychange listener.
     */
    void removeProperty(String propertyName);

    /**
     * Returns a {@link List} of {@link String}s containing all
     * property names.
     *
     * @return a {@link List} containing all property names.
     */
    List<String> getAllPropertyNames();

    /**
     * Returns a {@link List} of {@link String}s containing all property names
     * that have the specified prefix. The return value will include property
     * names that have prefixes longer than specified, unless the
     * {@code exactPrefixMatch} parameter is {@code true}.
     * <p>
     * Example:
     * <p>
     * Imagine a configuration service instance containing 2 properties only:<br>
     * {@code 
     * net.java.sip.communicator.PROP1=value1<br>
     * net.java.sip.communicator.service.protocol.PROP1=value2
     * }
     * <p>
     * A call to this method with a prefix="net.java.sip.communicator" and
     * exactPrefixMatch=true would only return the first property -
     * net.java.sip.communicator.PROP1, whereas the same call with
     * exactPrefixMatch=false would return both properties as the second prefix
     * includes the requested prefix string.
     * <p>
     * @param prefix a String containing the prefix (the non dotted non-caps
     * part of a property name) that we're looking for.
     * @param exactPrefixMatch a boolean indicating whether the returned
     * property names should all have a prefix that is an exact match of the
     * the {@code prefix} param or whether properties with prefixes that
     * contain it but are longer than it are also accepted.
     * @return a {@link List}containing all property name {@link String}s
     * matching the specified conditions.
     */
    List<String> getPropertyNamesByPrefix(String prefix,
                                          boolean exactPrefixMatch);

    /**
     * Returns a {@link List} of {@link String}s containing the property names
     * that have the specified suffix. A suffix is considered to be everything
     * after the last dot in the property name.
     * <p>
     * For example, imagine a configuration service instance containing two
     * properties only:
     * </p>
     * {@code 
     * net.java.sip.communicator.PROP1=value1
     * net.java.sip.communicator.service.protocol.PROP1=value2
     * }
     * <p>
     * A call to this method with {@code suffix} equal to "PROP1" will return
     * both properties, whereas the call with {@code suffix} equal to
     * "communicator.PROP1" or "PROP2" will return an empty {@link List}. Thus,
     * if the {@code suffix} argument contains a dot, nothing will be found.
     * </p>
     *
     * @param suffix the suffix for the property names to be returned
     * @return a {@link List} of {@link String}s containing the property names
     * which contain the specified {@code suffix}
     */
    List<String> getPropertyNamesBySuffix(String suffix);

    /**
     * Returns the String value of the specified property and null in case no
     * property value was mapped against the specified propertyName, or in
     * case the returned property string had zero length or contained
     * whitespaces only.
     *
     * @param propertyName the name of the property that is being queried.
     * @return the result of calling the property's toString method and null in
     * case there was no value mapped against the specified
     * {@code propertyName}, or the returned string had zero length or
     * contained whitespaces only.
     */
    String getString(String propertyName);

    /**
     * Returns the String value of the specified property and null in case no
     * property value was mapped against the specified propertyName, or in
     * case the returned property string had zero length or contained
     * whitespaces only.
     *
     * @param propertyName the name of the property that is being queried.
     * @param defaultValue the value to be returned if the specified property
     * name is not associated with a value in this
     * {@code ConfigurationService}
     * @return the result of calling the property's toString method and
     * {@code defaultValue} in case there was no value mapped against
     * the specified {@code propertyName}, or the returned string had zero
     * length or contained whitespaces only.
     */
    String getString(String propertyName, String defaultValue);

    /**
     * Gets the value of a specific property as a boolean. If the specified
     * property name is associated with a value in this
     * {@code ConfigurationService}, the string representation of the value
     * is parsed into a boolean according to the rules of
     * {@link Boolean#parseBoolean(String)} . Otherwise,
     * {@code defaultValue} is returned.
     *
     * @param propertyName
     *            the name of the property to get the value of as a boolean
     * @param defaultValue
     *            the value to be returned if the specified property name is not
     *            associated with a value in this
     *            {@code ConfigurationService}
     * @return the value of the property with the specified name in this
     *         {@code ConfigurationService} as a boolean;
     *         {@code defaultValue} if the property with the specified name
     *         is not associated with a value in this
     *         {@code ConfigurationService}
     */
    boolean getBoolean(String propertyName, boolean defaultValue);

    /**
     * Gets the value of a specific property as a signed decimal integer. If the
     * specified property name is associated with a value in this
     * {@link ConfigurationService}, the string representation of the value is
     * parsed into a signed decimal integer according to the rules of
     * {@link Integer#parseInt(String)} . If parsing the value as a signed
     * decimal integer fails or there is no value associated with the specified
     * property name, {@code defaultValue} is returned.
     *
     * @param propertyName the name of the property to get the value of as a
     * signed decimal integer
     * @param defaultValue the value to be returned if parsing the value of the
     * specified property name as a signed decimal integer fails or there is no
     * value associated with the specified property name in this
     * {@link ConfigurationService}
     * @return the value of the property with the specified name in this
     * {@link ConfigurationService} as a signed decimal integer;
     * {@code defaultValue} if parsing the value of the specified property name
     * fails or no value is associated in this {@link ConfigurationService}
     * with the specified property name
     */
    int getInt(String propertyName, int defaultValue);

    /**
     * Gets the value of a specific property as a double. If the specified
     * property name is associated with a value in this
     * {@link ConfigurationService}, the string representation of the value is
     * parsed into a double according to the rules of {@link
     * Double#parseDouble(String)}. If there is no value, or parsing of the
     * value fails, {@code defaultValue} is returned.
     *
     * @param propertyName the name of the property.
     * @param defaultValue the default value to be returned.
     * @return the value of the property with the specified name in this
     * {@link ConfigurationService} as a double, or {@code defaultValue}.
     */
    double getDouble(String propertyName, double defaultValue);

    /**
     * Gets the value of a specific property as a signed decimal long integer.
     * If the specified property name is associated with a value in this
     * {@link ConfigurationService}, the string representation of the value is
     * parsed into a signed decimal long integer according to the rules of
     * {@link Long#parseLong(String)} . If parsing the value as a signed
     * decimal long integer fails or there is no value associated with the
     * specified property name, {@code defaultValue} is returned.
     *
     * @param propertyName the name of the property to get the value of as a
     * signed decimal long integer
     * @param defaultValue the value to be returned if parsing the value of the
     * specified property name as a signed decimal long integer fails or there
     * is no value associated with the specified property name in this
     * {@link ConfigurationService}
     * @return the value of the property with the specified name in this
     * {@link ConfigurationService} as a signed decimal long integer;
     * {@code defaultValue} if parsing the value of the specified property name
     * fails or no value is associated in this {@link ConfigurationService}
     * with the specified property name
     */
    long getLong(String propertyName, long defaultValue);

    /**
     * Adds a {@link PropertyChangeListener} to the listener list. The listener
     * is registered for all properties in the current configuration.
     * <p>
     * @param listener the {@link PropertyChangeListener} to be added
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Removes a {@link PropertyChangeListener} from the listener list.
     * <p>
     * @param listener the {@link PropertyChangeListener} to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Adds a PropertyChangeListener to the listener list for a specific
     * property. In case a property with the specified name does not exist the
     * listener is still added and would only be taken into account from the
     * moment such a property is set by someone.
     * <p>
     * @param propertyName one of the property names listed above
     * @param listener the PropertyChangeListener to be added
     */
    void addPropertyChangeListener(String propertyName,
                                   PropertyChangeListener listener);

    /**
     * Removes a PropertyChangeListener from the listener list for a specific
     * property. This method should be used to remove PropertyChangeListeners
     * that were registered for a specific property. The method has no effect
     * when called for a listener that was not registered for that specific
     * property.
     * <p>
     *
     * @param propertyName a valid property name
     * @param listener the PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(String propertyName,
                                      PropertyChangeListener listener);

    /**
     * Adds a VetoableChangeListener to the listener list. The listener is
     * registered for all properties in the configuration.
     * @param listener the VetoableChangeListener to be added
     */
    void addVetoableChangeListener(ConfigVetoableChangeListener listener);

    /**
     * Removes a VetoableChangeListener from the listener list.
     *
     * @param listener the VetoableChangeListener to be removed
     */
    void removeVetoableChangeListener(ConfigVetoableChangeListener listener);

    /**
     * Adds a VetoableChangeListener to the listener list for a specific
     * property.
     *
     * @param propertyName one of the property names listed above
     * @param listener the VetoableChangeListener to be added
     */
    void addVetoableChangeListener(String propertyName,
                                   ConfigVetoableChangeListener listener);

    /**
     * Removes a VetoableChangeListener from the listener list for a specific
     * property.
     *
     * @param propertyName a valid property name
     * @param listener the VetoableChangeListener to be removed
     */
    void removeVetoableChangeListener(String propertyName,
                                      ConfigVetoableChangeListener listener);

    /**
     * Store the current set of properties back to the configuration file. The
     * name of the configuration file is queried from the system property
     * net.java.sip.communicator.PROPERTIES_FILE_NAME, and is set to
     * sip-communicator.xml in case the property does not contain a valid file
     * name. The location might be one of three possible, checked in the
     * following order: <br>
     * <ol>
     * <li>The current directory.
     * <li>The sip-communicator directory in the user.home
     *    ($HOME/.sip-communicator)
     * <li>A location in the classpath (such as the sip-communicator jar file).
     * </ol>
     * <p>
     * In the last case the file is copied to the sip-communicator configuration
     * directory right after being extracted from the classpath location.
     *
     * @throws IOException in case storing the configuration failed.
     */
    void storeConfiguration()
        throws IOException;

    /**
     * Deletes the current configuration and reloads it from the configuration
     * file. The name of the configuration file is queried from the system
     * property net.java.sip.communicator.PROPERTIES_FILE_NAME, and is set to
     * sip-communicator.xml in case the property does not contain a valid file
     * name. The location might be one of three possible, checked in the
     * following order: <br>
     * <ol>
     * <li>The current directory.
     * <li>The sip-communicator directory in the user.home
     *    ($HOME/.sip-communicator)
     * <li>A location in the classpath (such as the sip-communicator jar file).
     * </ol>
     * <p>
     * In the last case the file is copied to the sip-communicator configuration
     * directory right after being extracted from the classpath location.
     * @throws IOException in case reading the configuration fails.
     */
    void reloadConfiguration()
        throws IOException;

    /**
     * Removes all locally stored properties leaving an empty configuration.
     * Implementations that use a file for storing properties may simply delete
     * it when this method is called.
     */
    void purgeStoredConfiguration();

    /**
     * Prints all configuration properties on 'INFO' logging level *except*
     * that properties which name matches given regular expression will have
     * their values masked with ***.
     *
     * @param passwordPattern regular expression which detects properties which
     *                        values should be masked.
     */
    void logConfigurationProperties(String passwordPattern);

    /**
     * Returns the name of the directory where Jitsi is to store user
     * specific data such as configuration files, message and call history
     * as well as is bundle repository.
     *
     * @return the name of the directory where Jitsi is to store
     * user specific data such as configuration files, message and call history
     * as well as is bundle repository.
     */
    String getScHomeDirName();

    /**
     * Returns the location of the directory where Jitsi is to store
     * user specific data such as configuration files, message and call history
     * as well as is bundle repository.
     *
     * @return the location of the directory where Jitsi is to store
     * user specific data such as configuration files, message and call history
     * as well as is bundle repository.
     */
    String getScHomeDirLocation();

    /**
     * Use with caution!
     * Returns the name of the configuration file currently
     * used. Placed in HomeDirLocation/HomeDirName
     * {@link #getScHomeDirLocation()}
     * {@link #getScHomeDirName()}
     * @return  the name of the configuration file currently used.
     */
    String getConfigurationFilename();
}
