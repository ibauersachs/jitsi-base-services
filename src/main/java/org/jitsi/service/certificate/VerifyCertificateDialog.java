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
package org.jitsi.service.certificate;

/**
 * Interface that dialogs for user certification approval must implement.
 */
public interface VerifyCertificateDialog
{
    /**
     * Shows or hides the dialog and waits for user response.
     * @param isVisible whether we should show or hide the dialog.
     */
    void setVisible(boolean isVisible);

    /**
     * Whether the user has accepted the certificate or not.
     * @return whether the user has accepted the certificate or not.
     */
    boolean isTrusted();

    /**
     * Whether the user has selected to note the certificate so we always
     * trust it.
     * @return whether the user has selected to note the certificate so
     * we always trust it.
     */
    boolean isAlwaysTrustSelected();
}
