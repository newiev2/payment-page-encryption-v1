package com.itechpsp.sdk;

public class Main {

    private final static String SECRET = "***";
    private final static String ENCRYPTION_KEY = "***";
    private final static String BASE_URL = "https://paymentpage.trxhost.com";

    public static void main(String[] args) {
        Gate gate = new Gate(SECRET, ENCRYPTION_KEY, BASE_URL);

        Payment payment = new Payment("53731");

        payment.setParam(Payment.PAYMENT_ID, "666777");
        payment.setParam(Payment.PAYMENT_AMOUNT, "500");
        payment.setParam(Payment.PAYMENT_CURRENCY, "RUB");
        payment.setParam(Payment.CUSTOMER_ID, "1");

        String url = gate.getPurchasePaymentPageUrl(payment);
        System.out.println(url);
    }
}
