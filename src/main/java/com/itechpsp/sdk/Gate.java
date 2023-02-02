package com.itechpsp.sdk;

/**
 * Class for communicate with our
 */
public class Gate {
    /**
     * com.trxhosts.sdk.SignatureHandler instance for check signature
     */
    private SignatureHandler signatureHandler;

    /**
     * com.trxhosts.sdk.PaymentPage instance for build payment URL
     */
    private PaymentPage paymentPageUrlBuilder;

    /**
     * com.trxhosts.sdk.Gate constructor
     * @param secret site salt
     * @param encryptionKey site encryption key
     */
    public Gate(String secret, String encryptionKey, String baseUrl) {
        signatureHandler = new SignatureHandler(secret);
        paymentPageUrlBuilder = new PaymentPage(signatureHandler, encryptionKey, baseUrl);
    }

    /**
     * Method build payment URL
     * @param payment com.trxhosts.sdk.Payment instance with payment params
     * @return string URL that you can use for redirect on payment page
     */
    public String getPurchasePaymentPageUrl(Payment payment) {
        return paymentPageUrlBuilder.getUrl(payment);
    }

    /**
     * Method for handling callback
     * @param data raw callback data in JSON format
     * @return com.trxhosts.sdk.Callback instance
     * @throws ProcessException throws when signature is invalid
     */
    public Callback handleCallback(String data) throws ProcessException {
        return new Callback(data, signatureHandler);
    }
}
