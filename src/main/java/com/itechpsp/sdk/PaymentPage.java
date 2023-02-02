package com.itechpsp.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

/**
 * Class for build payment URL
 */
public class PaymentPage {
    /**
     * Encoding charset
     */
    private final String CHARSET = "UTF-8";

    /**
     * payment domain with path
     */
    private String baseUrl;

    /**
     * Signature handler for generate signature
     */
    private SignatureHandler signatureHandler;

    private Encryptor encryptor;

    /**
     * com.trxhosts.sdk.PaymentPage constructor
     * @param signHandler signature handler for generate signature
     * @param key encryption key to encrypt query data
     */
    public PaymentPage(SignatureHandler signHandler, String key, String baseUrl) {
        signatureHandler = signHandler;
        encryptor = new Encryptor(key);
        this.baseUrl = baseUrl;
    }

    /**
     * Method build payment URL
     * @param payment com.trxhosts.sdk.Payment instance with payment params
     * @return string URL that you can use for redirect on payment page
     */
    public String getUrl(Payment payment) {
        String signature = "&signature=".concat(encode(signatureHandler.sign(payment.getParams())));
        String query = payment.getParams().entrySet().stream()
            .map(e -> e.getKey() + "=" + encode(e.getValue()))
            .collect(Collectors.joining("&"));
        String notEncryptedUrl = baseUrl.concat("?").concat(query).concat(signature);

        try {
            URL url = new URL(notEncryptedUrl);
            String linkToEncrypt = url.getPath() + "?" + url.getQuery();
            encryptor.encryptPaymentLink(linkToEncrypt);
            return url.getProtocol() + "://" + url.getHost() + "/" + payment.getProjectId() + "/"
                    + encryptor.encryptPaymentLink(linkToEncrypt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for URL encoding payment params
     * @param param payment param value
     * @return URL encoded param
     */
    private String encode(Object param) {
        try {
            return URLEncoder.encode(param.toString(), CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
