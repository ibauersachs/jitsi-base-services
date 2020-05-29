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

import java.security.cert.Certificate;

/**
 * Service that creates dialog that is shown to the user when
 * a certificate verification failed.
 *
 * @author Damian Minkov
 */
public interface VerifyCertificateDialogService
{
    /**
     * Creates the dialog.
     *
     * @param certs the certificates list
     * @param title The title of the dialog; when null the resource
     * {@code service.gui.CERT_DIALOG_TITLE} is loaded and used.
     * @param message A text that describes why the verification failed.
     * @return a dialog to ask the user if the trusts the certificate.
     */
    VerifyCertificateDialog createDialog(
            Certificate[] certs, String title, String message);
}
