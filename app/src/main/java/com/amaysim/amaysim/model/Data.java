package com.amaysim.amaysim.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Data implements Serializable{
    public String id;
    public String type;
    public String paymentType;
    public String unbilledCharges;
    public String nextBillingDate;
    public String title;
    public String firstName;
    public String lastName;
    public String dateOfBirth;
    public String contactNumber;
    public String emailAddress;
    public Boolean emailAddressVerified;
    public Boolean emailSubscriptionStatus;
    public String links;
    public String relationships;

    public String name;
    public String includedData;
    public String includedCredit;
    public String includedInternationalTalk;
    public Boolean unlimitedText;
    public Boolean unlimitedTalk;
    public Boolean unlimitedInternationalText;
    public Boolean unlimitedInternationalTalk;
    public String price;

    public String includedDataBalance;
    public String includedCreditBalance;
    public String includedRolloverCreditBalance;
    public String includedRolloverDataBalance;
    public String includedInternationalTalkBalance;
    public String expiryDate;
    public Boolean autoRenewal;
    public Boolean primarySubscription;

    public String msn;
    public String credit;
    public String creditExpiry;
    public Boolean dataUsageTreshold;

    public Data(String json){
        try {
            JSONObject data = new JSONObject(json);
            String attributes = data.getString("attributes");
            JSONObject jsonAttributes = new JSONObject(attributes);
            id = data.getString("id");
            type = data.getString("type");
            if (type.equals(TYPE.ACCOUNT)){
                links = data.getString("links");
                paymentType = jsonAttributes.getString("payment-type");
                unbilledCharges = jsonAttributes.getString("unbilled-charges");
                nextBillingDate = jsonAttributes.getString("next-billing-date");
                title = jsonAttributes.getString("title");
                firstName = jsonAttributes.getString("first-name");
                lastName = jsonAttributes.getString("last-name");
                dateOfBirth = jsonAttributes.getString("date-of-birth");
                contactNumber = jsonAttributes.getString("contact-number");
                emailAddress = jsonAttributes.getString("email-address");
                emailAddressVerified = jsonAttributes.getBoolean("email-address-verified");
                emailSubscriptionStatus = jsonAttributes.getBoolean("email-subscription-status");
            } else if(type.equals(TYPE.PRODUCT)){
                name = jsonAttributes.getString("name");
                includedData = jsonAttributes.getString("included-data");
                includedCredit = jsonAttributes.getString("included-credit");
                includedInternationalTalk = jsonAttributes.getString("included-international-talk");
                unlimitedText = jsonAttributes.getBoolean("unlimited-text");
                unlimitedTalk = jsonAttributes.getBoolean("unlimited-talk");
                unlimitedInternationalText = jsonAttributes.getBoolean("unlimited-international-text");
                unlimitedInternationalTalk = jsonAttributes.getBoolean("unlimited-international-talk");
                price = jsonAttributes.getString("price");
            } else if(type.equals(TYPE.SUBSCRIPTION)){
                includedDataBalance = jsonAttributes.getString("included-data-balance");
                includedCreditBalance = jsonAttributes.getString("included-credit-balance");
                includedRolloverCreditBalance = jsonAttributes.getString("included-rollover-credit-balance");
                includedRolloverDataBalance = jsonAttributes.getString("included-rollover-data-balance");
                includedInternationalTalkBalance = jsonAttributes.getString("included-international-talk-balance");
                expiryDate = jsonAttributes.getString("expiry-date");
                autoRenewal = jsonAttributes.getBoolean("auto-renewal");
                primarySubscription = jsonAttributes.getBoolean("primary-subscription");
            } else if(type.equals(TYPE.SERVICE)){
                msn = jsonAttributes.getString("msn");
                credit = jsonAttributes.getString("credit");
                creditExpiry = jsonAttributes.getString("credit-expiry");
                dataUsageTreshold = jsonAttributes.getBoolean("data-usage-threshold");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class TYPE{
        public static final String ACCOUNT = "accounts";
        public static final String SERVICE = "services";
        public static final String SUBSCRIPTION = "subscriptions";
        public static final String PRODUCT = "products";
    }

}
