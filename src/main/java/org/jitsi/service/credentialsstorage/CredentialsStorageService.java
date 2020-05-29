/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
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
package org.jitsi.service.credentialsstorage;

/**
 * Loads and saves user credentials from/to the persistent storage
 * (configuration file in the default implementation).
 *
 * @author Dmitri Melnikov
 */
public interface CredentialsStorageService
{
    /**
     * Store the password for the account that starts with the given prefix.
     *
     * @param accountPrefix account prefix
     * @param password the password to store
     * @return {@code true} if the specified {@code password} was successfully
     * stored; otherwise, {@code false}
     */
    boolean storePassword(String accountPrefix, String password);

    /**
     * Load the password for the account that starts with the given prefix.
     *
     * @param accountPrefix account prefix
     * @return the loaded password for the {@code accountPrefix}
     */
    String loadPassword(String accountPrefix);

    /**
     * Remove the password for the account that starts with the given prefix.
     *
     * @param accountPrefix account prefix
     * @return {@code true} if the password for the specified
     * {@code accountPrefix} was successfully removed; otherwise,
     * {@code false}
     */
    boolean removePassword(String accountPrefix);

    /**
     * Checks if master password was set by the user and
     * it is used to encrypt saved account passwords.
     *
     * @return {@code true} if used, {@code false} if not
     */
    boolean isUsingMasterPassword();

    /**
     * Changes the old master password to the new one.
     * For all saved account passwords it decrypts them with the old MP and then
     * encrypts them with the new MP.
     *
     * @param oldPassword the old master password
     * @param newPassword the new master password
     * @return {@code true} if master password was changed successfully;
     * {@code false}, otherwise
     */
    boolean changeMasterPassword(String oldPassword, String newPassword);

    /**
     * Verifies the correctness of the master password.
     *
     * @param master the master password to verify
     * @return {@code true} if the password is correct; {@code false},
     * otherwise
     */
    boolean verifyMasterPassword(String master);

    /**
     * Checks if the account password that starts with the given prefix is saved
     * in encrypted form.
     *
     * @param accountPrefix account prefix
     * @return {@code true} if saved, {@code false} if not
     */
    boolean isStoredEncrypted(String accountPrefix);
}
