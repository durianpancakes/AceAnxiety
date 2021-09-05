package com.durianpancakes.mindfulhacks2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.durianpancakes.mindfulhacks2.ui.chat.ChatInterface;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.durianpancakes.mindfulhacks2.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChatInterface {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseHelper mDatabase;
    private FirebaseUser mUser;
    private ChatBot mChatBot;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    /**
     * Launches Firebase AuthUI for login
     */
    private void startAuth() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();

        signInLauncher.launch(signInIntent);
    }

    /**
     * Signs out the user and launches Firebase AuthUI for login
     */
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startAuth();
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            mUser = FirebaseAuth.getInstance().getCurrentUser();
            // Successfully signed in
            Log.d("FirebaseAuth", "User "
                    + mUser.getDisplayName()
                    + " has successfully signed in");
            mDatabase = FirebaseHelper.getINSTANCE();
            mDatabase.addNewUser(mUser.getDisplayName());
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            if (response == null) {
                // User canceled the sign-in flow using the back button
            } else {
                int errorCode = response.getError().getErrorCode();
                Log.e("FirebaseAuth", "There was an error signing in. "
                        + "Error Code: " + errorCode);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mChatBot = ChatBot.getINSTANCE();

        if (mUser == null) {
            Log.d("FirebaseAuth", "User is not logged in");
            startAuth();
        } else {
            Log.d("FirebaseAuth", "User "
                    + mUser.getDisplayName()
                    + " has signed in before.");
            mDatabase = FirebaseHelper.getINSTANCE();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_chat, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onSendButtonClicked(String message) {
        Message message1 = new Message(message, "user", new Timestamp(System.currentTimeMillis()).toString());
        mDatabase.addNewMessage(message1);

        String botResponse = mChatBot.respondToUser(message1);
        if (botResponse.contentEquals("")) {
            return;
        } else {
            Message message2 = new Message(botResponse, "bot", new Timestamp(System.currentTimeMillis()).toString());
            mDatabase.addNewMessage(message2);
        }
    }
}