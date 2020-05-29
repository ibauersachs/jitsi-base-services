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

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509ExtendedTrustManager;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.List;

/**
 * A service which implementors will ask the user for permission for the
 * certificates which are for some reason not valid and not globally trusted.
 *
 * @author Damian Minkov
 * @author Ingo Bauersachs
 */
public interface CertificateService
{
    // ------------------------------------------------------------------------
    // Configuration property names
    // ------------------------------------------------------------------------
    /**
     * Property for always trust mode. When enabled certificate check is
     * skipped.
     */
    String PNAME_ALWAYS_TRUST =
        "net.java.sip.communicator.service.gui.ALWAYS_TRUST_MODE_ENABLED";

    /**
     * When set to true, the certificate check is performed. If the check fails
     * the user is not asked and the error is directly reported to the calling
     * service.
     */
    String PNAME_NO_USER_INTERACTION =
        "net.java.sip.communicator.service.tls.NO_USER_INTERACTION";

    /**
     * The property name prefix of all client authentication configurations.
     */
    String PNAME_CLIENTAUTH_CERTCONFIG_BASE =
        "net.java.sip.communicator.service.cert.clientauth";

    /**
     * Property that is being applied to the system property
     * {@code javax.net.ssl.trustStoreType}
     */
    String PNAME_TRUSTSTORE_TYPE =
        "net.java.sip.communicator.service.cert.truststore.type";

    /**
     * Property that is being applied to the system property
     * {@code javax.net.ssl.trustStore}
     */
    String PNAME_TRUSTSTORE_FILE =
        "net.java.sip.communicator.service.cert.truststore.file";

    /**
     * Property that is being applied to the system property
     * {@code javax.net.ssl.trustStorePassword}
     */
    String PNAME_TRUSTSTORE_PASSWORD =
        "net.java.sip.communicator.service.cert.truststore.password";

    /**
     * Property that is being applied to the system properties
     * {@code com.sun.net.ssl.checkRevocation} and
     * {@code com.sun.security.enableCRLDP}
     */
    String PNAME_REVOCATION_CHECK_ENABLED =
        "net.java.sip.communicator.service.cert.revocation.enabled";

    /**
     * Property that is being applied to the Security property
     * {@code ocsp.enable}
     */
    String PNAME_OCSP_ENABLED =
        "net.java.sip.communicator.service.cert.ocsp.enabled";

    // ------------------------------------------------------------------------
    // Client authentication configuration
    // ------------------------------------------------------------------------
    /**
     * Returns all saved {@link CertificateConfigEntry}s.
     *
     * @return List of the saved authentication configurations.
     */
    List<CertificateConfigEntry> getClientAuthCertificateConfigs();

    /**
     * Deletes a saved {@link CertificateConfigEntry}.
     *
     * @param id The ID ({@link CertificateConfigEntry#getId()}) of the entry to
     *            delete.
     */
    void removeClientAuthCertificateConfig(String id);

    /**
     * Saves or updates the passed {@link CertificateConfigEntry} to the config.
     * If {@link CertificateConfigEntry#getId()} returns null, a new entry is
     * created.
     *
     * @param entry The @see CertificateConfigEntry to save or update.
     */
    void setClientAuthCertificateConfig(CertificateConfigEntry entry);

    /**
     * Gets a list of all supported KeyStore types.
     *
     * @return a list of all supported KeyStore types.
     */
    List<KeyStoreType> getSupportedKeyStoreTypes();

    // ------------------------------------------------------------------------
    // Certificate trust handling
    // ------------------------------------------------------------------------
    /**
     * Get an SSL Context that validates certificates based on the JRE default
     * check and asks the user when the JRE check fails.
     *
     * CAUTION: Only the certificate itself is validated, no check is performed
     * whether it is valid for a specific server or client.
     *
     * @return An SSL context based on a user confirming trust manager.
     * @throws GeneralSecurityException when something went wrong
     */
    SSLContext getSSLContext() throws GeneralSecurityException;

    /**
     * Get an SSL Context with the specified trustmanager.
     *
     * @param trustManager The trustmanager that will be used by the created
     *            SSLContext
     * @return An SSL context based on the supplied trust manager.
     * @throws GeneralSecurityException when something went wrong
     */
    SSLContext getSSLContext(X509ExtendedTrustManager trustManager)
        throws GeneralSecurityException;

    /**
     * Get an SSL Context with the specified trustmanager.
     *
     * @param clientCertConfig The ID of a client certificate configuration
     *            entry that is to be used when the server asks for a client TLS
     *            certificate
     * @param trustManager The trustmanager that will be used by the created
     *            SSLContext
     * @return An SSL context based on the supplied trust manager.
     * @throws GeneralSecurityException when something went wrong
     */
    SSLContext getSSLContext(String clientCertConfig,
                             X509ExtendedTrustManager trustManager)
        throws GeneralSecurityException;

    /**
     * Get an SSL Context with the specified trustmanager.
     *
     * @param keyManagers The key manager(s) to be used for client
     *            authentication
     * @param trustManager The trustmanager that will be used by the created
     *            SSLContext
     * @return An SSL context based on the supplied trust manager.
     * @throws GeneralSecurityException when something went wrong
     */
    SSLContext getSSLContext(KeyManager[] keyManagers,
                             X509ExtendedTrustManager trustManager)
        throws GeneralSecurityException;

    /**
     * Creates a trustmanager that validates the certificate based on the JRE
     * default check and asks the user when the JRE check fails. When
     * {@code null} is passed as the {@code identityToTest} then no check is
     * performed whether the certificate is valid for a specific server or
     * client. The passed identities are checked by applying a behavior similar
     * to the on regular browsers use.
     *
     * @param identitiesToTest when not {@code null}, the values are assumed
     *            to be hostnames for invocations of checkServerTrusted and
     *            e-mail addresses for invocations of checkClientTrusted
     * @return TrustManager to use in an SSLContext
     * @throws GeneralSecurityException when something went wrong
     */
    X509ExtendedTrustManager getTrustManager(Iterable<String> identitiesToTest)
        throws GeneralSecurityException;

    /**
     * @see #getTrustManager(Iterable)
     *
     * @param identityToTest when not {@code null}, the value is assumed to
     *            be a hostname for invocations of checkServerTrusted and an
     *            e-mail address for invocations of checkClientTrusted
     * @return TrustManager to use in an SSLContext
     * @throws GeneralSecurityException when something went wrong
     */
    X509ExtendedTrustManager getTrustManager(String identityToTest)
        throws GeneralSecurityException;

    /**
     * @see #getTrustManager(Iterable, CertificateMatcher, CertificateMatcher)
     *
     * @param identityToTest The identity to match against the supplied
     *            verifiers.
     * @param clientVerifier The verifier to use in calls to checkClientTrusted
     * @param serverVerifier The verifier to use in calls to checkServerTrusted
     * @return TrustManager to use in an SSLContext
     * @throws GeneralSecurityException when something went wrong
     */
    X509ExtendedTrustManager getTrustManager(
            final String identityToTest,
            final CertificateMatcher clientVerifier,
            final CertificateMatcher serverVerifier)
        throws GeneralSecurityException;

    /**
     * Creates a trustmanager that validates the certificate based on the JRE
     * default check and asks the user when the JRE check fails. When
     * {@code null} is passed as the {@code identityToTest} then no check is
     * performed whether the certificate is valid for a specific server or
     * client.
     *
     * @param identitiesToTest The identities to match against the supplied
     *            verifiers.
     * @param clientVerifier The verifier to use in calls to checkClientTrusted
     * @param serverVerifier The verifier to use in calls to checkServerTrusted
     * @return TrustManager to use in an SSLContext
     * @throws GeneralSecurityException when something went wrong
     */
    X509ExtendedTrustManager getTrustManager(
            final Iterable<String> identitiesToTest,
            final CertificateMatcher clientVerifier,
            final CertificateMatcher serverVerifier)
        throws GeneralSecurityException;

    /**
     * Adds a certificate to the local trust store.
     *
     * @param cert The certificate to add to the trust store.
     * @param trustFor the domain for which the certificate should be trusted
     * @param trustMode Whether to trust the certificate permanently or only
     *            for the current session.
     * @throws CertificateException when the thumbprint could not be calculated
     */
    void addCertificateToTrust(Certificate cert,
                               String trustFor,
                               CertificateTrustResponse trustMode)
            throws CertificateException;
}
