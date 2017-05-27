package com.amaysim.amaysim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amaysim.amaysim.controller.Converter;
import com.amaysim.amaysim.model.Data;

import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity {
    private static final String TAG = "InformationActivity";
    private TextView mFullName, mBasicDetails, mServiceDetails,
            mSubscriptionDetails, mProductDetails;
    private String mBasicString, mServiceString, mSubscriptionString, mProductString;
    private CheckBox mEmailAddressVerified, mEmailSubscriptionStatus,
            mDataUsageTreshold,
            mAutoRenewal, mPrimarySubscription,
            mUnlimitedText, mUnlimitedTalk, mUnlimitedInternationalText, mUnlimitedInternationalTalk;
    private Data account;
    private ArrayList<Data> included;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        account = (Data) getIntent().getSerializableExtra("data");
        included = (ArrayList<Data>) getIntent().getSerializableExtra("included");

        prepareBasicInfo();
        prepareService();
        prepareSubscription();
        prepareProduct();
    }

    private void prepareService() {
        mServiceDetails = (TextView) findViewById(R.id.tvService);
        mDataUsageTreshold = (CheckBox) findViewById(R.id.cbDataUsageTreshold);
    }

    private void prepareBasicInfo() {
        mFullName = (TextView) findViewById(R.id.tvName);
        mBasicDetails = (TextView) findViewById(R.id.tvBasic);
        mSubscriptionDetails = (TextView) findViewById(R.id.tvSubscription);
        mProductDetails = (TextView) findViewById(R.id.tvProducts);

        mEmailAddressVerified = (CheckBox) findViewById(R.id.cbEmailAddressVerified);
        mEmailSubscriptionStatus = (CheckBox) findViewById(R.id.cbEmailSubscriptionStatus);
    }

    private void prepareSubscription(){
        mSubscriptionDetails = (TextView) findViewById(R.id.tvSubscription);
        mAutoRenewal = (CheckBox) findViewById(R.id.cbAutoRenewal);
        mPrimarySubscription = (CheckBox) findViewById(R.id.cbPrimarySubscription);
    }

    private void prepareProduct(){
        mProductDetails = (TextView) findViewById(R.id.tvProducts);
        mUnlimitedTalk = (CheckBox) findViewById(R.id.cbUnlimitedTalk);
        mUnlimitedText = (CheckBox) findViewById(R.id.cbUnlimitedText);
        mUnlimitedInternationalTalk = (CheckBox) findViewById(R.id.cbUnlimitedInternationalTalk);
        mUnlimitedInternationalText = (CheckBox) findViewById(R.id.cbUnlimitedInternationalText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateBasic();
        populateService();
        populateSubscription();
        populateProduct();
    }

    private void populateBasic() {
        mFullName.setText(account.title + " " + account.firstName + " " + account.lastName);

        mBasicString = getResources().getString(R.string.payment_type)+ " " +account.paymentType
                + "\n" + getResources().getString(R.string.unbilled_charges)+ " " + Converter.dollar(account.unbilledCharges)
                + "\n" + getResources().getString(R.string.date_of_birth)+ " " + account.dateOfBirth
                + "\n" + getResources().getString(R.string.contact_number)+ " " + account.contactNumber
                + "\n" + getResources().getString(R.string.email_address)+ " " + account.emailAddress
                + "\n" + getResources().getString(R.string.next_billing_date)+ " " + account.nextBillingDate;

        mBasicDetails.setText(mBasicString);
        mEmailAddressVerified.setChecked(account.emailAddressVerified);
        mEmailSubscriptionStatus.setChecked(account.emailSubscriptionStatus);
    }

    private void populateService(){
        for (Data item : included) {
            if (item.type.equals(Data.TYPE.SERVICE)){
                mServiceString = "Credit: " + Converter.dollar(item.credit)
                + "\nCredit expiry: " + item.creditExpiry;
                mServiceDetails.setText(mServiceString);
                mDataUsageTreshold.setChecked(item.dataUsageTreshold);
            }
        }
    }

    private void populateSubscription(){
        for (Data item : included) {
            if (item.type.equals(Data.TYPE.SUBSCRIPTION)){
                mSubscriptionString = "Included data balance: " + Converter.mbToGb(item.includedDataBalance)
                        + "\nCredit balance: " + Converter.mbToGb(item.includedCreditBalance)
                        + "\nRollover credit balance: " + Converter.mbToGb(item.includedRolloverCreditBalance)
                        + "\nRollover data balance: " + Converter.mbToGb(item.includedRolloverDataBalance)
                        + "\nInternational talk balance: " + Converter.mbToGb(item.includedInternationalTalkBalance);
                mSubscriptionDetails.setText(mSubscriptionString);
                mAutoRenewal.setChecked(item.autoRenewal);
                mPrimarySubscription.setChecked(item.primarySubscription);
            }
        }
    }

    private void populateProduct(){
        for (Data item : included) {
            if (item.type.equals(Data.TYPE.PRODUCT)){
                mProductString = "Name: " + item.name
                        + "\nData: " + Converter.mbToGb(item.includedData)
                        + "\nCredit: " + Converter.mbToGb(item.includedCredit)
                        + "\nInternational talk: " + Converter.mbToGb(item.includedInternationalTalk)
                        + "\nPrice: " + Converter.dollar(item.price);
                mProductDetails.setText(mProductString);
                mUnlimitedInternationalText.setChecked(item.unlimitedInternationalText);
                mUnlimitedInternationalTalk.setChecked(item.unlimitedInternationalTalk);
                mUnlimitedText.setChecked(item.unlimitedText);
                mUnlimitedTalk.setChecked(item.unlimitedTalk);
            }
        }
    }
}
