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
 * Defines how a certificate that has been presented to the user
 * should be trusted.
 */
public enum CertificateTrustResponse
{
    /**
     * Do not trust this certificate.
     */
    DO_NOT_TRUST,

    /**
     * Always trust this certificate, now and in future encounters.
     */
    TRUST_ALWAYS,

    /**
     * Trust this certificate only for the current session.
     */
    TRUST_THIS_SESSION_ONLY
}
