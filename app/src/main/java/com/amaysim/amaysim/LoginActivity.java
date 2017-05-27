package com.amaysim.amaysim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.amaysim.amaysim.model.Collection;
import com.amaysim.amaysim.model.Data;

import java.util.ArrayList;
import java.util.Collections;

public class LoginActivity extends AppCompatActivity {
    private EditText msn;
    private Button mLogin;
    private Collection mCollection;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        msn = (EditText) findViewById(R.id.etMSNNumber);
        msn.setOnKeyListener(new LoginKey());
        mLogin = (Button) findViewById(R.id.btnLogin);
        mLogin.setOnClickListener(new LoginClick());

        mCollection = new Collection(LoginActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        msn.setText(mCollection.getIncluded()[0].msn);
    }

    private class LoginClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            login(view);
        }
    }

    private class LoginKey implements View.OnKeyListener {

        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                switch (i) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        login(view);
                    default:
                        break;
                }
            }
            return false;
        }
    }

    private void login(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Data[] data = mCollection.getIncluded();
        ArrayList<Data> list = new ArrayList<>();
        Collections.addAll(list, data);
        for (Data item : list) {
            if (item.type.equals(Data.TYPE.SERVICE)){

                if (!msn.getText().toString().equals("") &&
                        msn.getText().toString().equals(item.msn)) {
                    Data account = mCollection.getAccount();
                    Intent i = new Intent(LoginActivity.this, InformationActivity.class);
                    i.putExtra("data", account);
                    i.putExtra("included", list);
                    startActivity(i);
                } else if (msn.getText().toString().equals("")) {
                    Snackbar.make(msn, "Credential is empty.", Snackbar.LENGTH_LONG)
                            .setAction("", null)
                            .show();
                } else {
                    Snackbar.make(msn, "Account does not exist.", Snackbar.LENGTH_LONG)
                            .setAction("", null)
                            .show();
                }
                break;
            }
        }
    }
}
