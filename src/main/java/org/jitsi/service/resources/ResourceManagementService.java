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
package org.jitsi.service.resources;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;

/**
 * The Resource Management Service gives easy access to
 * common resources for the application including texts, images, sounds and
 * some configurations.
 *
 * @author Damian Minkov
 * @author Adam Netocny
 */
public interface ResourceManagementService
{
    // Color pack methods
    /**
     * Returns the int representation of the color corresponding to the
     * given key.
     *
     * @param key The key of the color in the colors properties file.
     * @return the int representation of the color corresponding to the
     * given key.
     */
    int getColor(String key);

    /**
     * Returns the string representation of the color corresponding to the
     * given key.
     *
     * @param key The key of the color in the colors properties file.
     * @return the string representation of the color corresponding to the
     * given key.
     */
    String getColorString(String key);

    /**
     * Returns the {@link InputStream} of the image corresponding to the given
     * path.
     *
     * @param path The path to the image file.
     * @return the {@link InputStream} of the image corresponding to the given
     * path.
     */
    InputStream getImageInputStreamForPath(String path);

    /**
     * Returns the {@link InputStream} of the image corresponding to the given
     * key.
     *
     * @param streamKey The identifier of the image in the resource properties
     * file.
     * @return the {@link InputStream} of the image corresponding to the given
     * key.
     */
    InputStream getImageInputStream(String streamKey);

    /**
     * Returns the {@link URL} of the image corresponding to the given key.
     *
     * @param urlKey The identifier of the image in the resource properties file.
     * @return the {@link URL} of the image corresponding to the given key
     */
    URL getImageURL(String urlKey);

    /**
     * Returns the {@link URL} of the image corresponding to the given path.
     *
     * @param path The path to the given image file.
     * @return the {@link URL} of the image corresponding to the given path.
     */
    URL getImageURLForPath(String path);

    /**
     * Returns the image path corresponding to the given key.
     *
     * @param key The identifier of the image in the resource properties file.
     * @return the image path corresponding to the given key.
     */
    String getImagePath(String key);

    // Language pack methods
    /**
     * Default Locale config string.
     */
    String DEFAULT_LOCALE_CONFIG =
            "net.java.sip.communicator.service.resources.DefaultLocale";

    /**
     * All the locales in the language pack.
     * @return all the locales this Language pack contains.
     */
    Iterator<Locale> getAvailableLocales();

    /**
     * Returns an internationalized string corresponding to the given key.
     *
     * @param key The identifier of the string in the resources properties file.
     * @return An internationalized string corresponding to the given key.
     */
    String getI18NString(String key);

    /**
     * Returns an internationalized string corresponding to the given key.
     *
     * @param key The identifier of the string in the resources properties file.
     * @param locale The locale.
     * @return An internationalized string corresponding to the given key and
     * given locale.
     */
    String getI18NString(String key, Locale locale);

    /**
     * Returns an internationalized string corresponding to the given key.
     *
     * @param key The identifier of the string in the resources properties file.
     * @param params An array of parameters to be replaced in the returned
     * string.
     * @return An internationalized string corresponding to the given key and
     * given locale.
     */
    String getI18NString(String key, String[] params);

    /**
     * Returns an internationalized string corresponding to the given key.
     *
     * @param key The identifier of the string in the resources properties file.
     * @param params An array of parameters to be replaced in the returned
     * string.
     * @param locale The locale.
     * @return An internationalized string corresponding to the given key.
     */
    String getI18NString(String key, String[] params, Locale locale);

    /**
     * Returns an internationalized string corresponding to the given key.
     *
     * @param key The identifier of the string in the resources properties file.
     * @return An internationalized string corresponding to the given key.
     */
    char getI18nMnemonic(String key);

    /**
     * Returns an internationalized string corresponding to the given key.
     *
     * @param key The key of the string.
     * @param l The locale.
     * @return An internationalized string corresponding to the given key.
     */
    char getI18nMnemonic(String key, Locale l);

    // Settings pack methods
    /**
     * Returns an url for the setting corresponding to the given key.
     * Used when the setting is an actual file.
     *
     * @param urlKey The key of the setting.
     * @return Url to the corresponding resource.
     */
    URL getSettingsURL(String urlKey);

    /**
     * Returns an InputStream for the setting corresponding to the given key.
     * Used when the setting is an actual file.
     *
     * @param streamKey The key of the setting.
     * @return InputStream to the corresponding resource.
     */
    InputStream getSettingsInputStream(String streamKey);

    /**
     * Returns a stream from a given identifier, obtained through the class
     * loader of the given resourceClass.
     *
     * @param streamKey The identifier of the stream.
     * @param resourceClass the resource class through which the resource would
     * be obtained
     * @return The stream for the given identifier.
     */
    InputStream getSettingsInputStream(String streamKey,
                                       Class<?> resourceClass);

    /**
     * Returns the int value of the corresponding configuration key.
     *
     * @param key The identifier of the string in the resources properties file.
     * @return the int value of the corresponding configuration key.
     */
    String getSettingsString(String key);

    /**
     * Returns the int value of the corresponding configuration key.
     *
     * @param key The identifier of the string in the resources properties file.
     * @return the int value of the corresponding configuration key.
     */
    int getSettingsInt(String key);

    // Sound pack methods
    /**
     * Returns an url for the sound resource corresponding to the given key.
     *
     * @param urlKey The key of the setting.
     * @return Url to the corresponding resource.
     */
    URL getSoundURL(String urlKey);

    /**
     * Returns an url for the sound resource corresponding to the given path.
     *
     * @param path The path to the sound resource.
     * @return Url to the corresponding resource.
     */
    URL getSoundURLForPath(String path);

    /**
     * Returns the path of the sound corresponding to the given
     * property key.
     *
     * @param soundKey The key of the sound.
     * @return the path of the sound corresponding to the given
     * property key.
     */
    String getSoundPath(String soundKey);

    /**
     * Constructs an {@link ImageIcon} from the specified image ID and returns
     * it.
     *
     * @param imageID The identifier of the image.
     * @return An {@link ImageIcon} containing the image with the given
     * identifier.
     */
    ImageIcon getImage(String imageID);

    /**
     * Loads the image with the specified ID and returns a byte array
     * containing it.
     *
     * @param imageID The identifier of the image.
     * @return A byte array containing the image with the given identifier.
     */
    byte[] getImageInBytes(String imageID);
}
