package com.example.opt_1.view;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.IViewToModel;
import com.example.opt_1.model.CRUDCallbacks;
import com.example.opt_1.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class LoginPage extends AppCompatActivity {
    private CompletableFuture<String> futureResult;
    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private Button registerButton;
    private IViewToModel controller;
    private String done = "";

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser fireUser = auth.getCurrentUser();
    private boolean languageChanged;
    FirebaseAuth.AuthStateListener authlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        setContentView(R.layout.login_page);




        //Language Selector Spinner.

        Spinner languageSpinner = findViewById(R.id.languageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selectedLanguage = parentView.getItemAtPosition(position).toString();

                    String languageCode = "";
                    Locale locale = null;
                    switch (selectedLanguage) {
                        case "English":
                            locale = new Locale("en");
                            languageCode = "en";
                            break;

                        case "Estonia":
                            locale = new Locale("et");
                            languageCode = "et";
                            break;

                        case "Swedish":
                            locale = new Locale("sv");
                            languageCode = "sv";
                            break;

                        case "Suomi":
                            locale = new Locale("fi");
                            languageCode = "fi";
                            break;

                        case "Persian":
                            locale = new Locale("fa");
                            languageCode = "fa";
                            break;

                        default:
                            locale = Locale.getDefault();
                            break;
                    }

                Locale newLocale = new Locale(languageCode);
                Locale currentLocale = getResources().getConfiguration().locale;

                if (!currentLocale.getLanguage().equals(languageCode)) {
                    Locale.setDefault(newLocale);
                    Configuration config = new Configuration();
                    config.locale = newLocale;

                    Resources resources = getResources();
                    resources.updateConfiguration(config, resources.getDisplayMetrics());
                    recreate();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });



        if(auth.getCurrentUser() != null){
            auth.signOut();
            System.out.println("Login page user: " + auth.getCurrentUser());
        }
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        //LoginPage Fields
        usernameField = findViewById(R.id.loginUserNameInput);
        passwordField = findViewById(R.id.loginPasswordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }});
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }


    private CompletableFuture<String> userLogin(){
        CompletableFuture<String> futureResult = new CompletableFuture<>();
            controller.userLogin(usernameField.getText().toString(), passwordField.getText().toString(), new CRUDCallbacks() {
                @Override
                public void onSucceed(){
                    System.out.println("User logged in: " + User.getInstance().getEmail());
                    Intent intent = new Intent(LoginPage.this, Main_Page.class);
                    startActivity(intent);
                    finish();
                    futureResult.complete("True");
                    System.out.println(futureResult);
                }

                @Override
                public void onFailure() {
                    futureResult.complete("False");
                    CharSequence text = "Email or Password is incorrect, or empty fields!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(LoginPage.this, text, duration);
                    toast.show();
                    System.out.println(futureResult);
                }
            });
            return futureResult;
    }
}
