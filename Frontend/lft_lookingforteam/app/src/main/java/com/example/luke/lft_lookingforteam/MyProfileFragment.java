package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;

/**
 * Fragment class that manages the user's profile viewing interface and operations
 */
public class MyProfileFragment extends Fragment {

    private Button editProfileBtn, logoutBtn, reportViewButton;
    private TextView usernameView, availabilityView;
    private ImageView profilePicView;
    private Intent i;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_profile_view_screen, container, false);

        // instantiate buttons
        editProfileBtn = view.findViewById(R.id.profileViewScreen_reportButton);
        logoutBtn = view.findViewById(R.id.myProfileViewScreen_logoutButton);
        reportViewButton = view.findViewById(R.id.ReportViewButton);

        // set edit profile button to switch to profile editing screen when pressed
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // switch to profile editing screen
                i = new Intent(getActivity(), EditProfileScreen.class);
                startActivity(i);
            }
        });

        // set logout button to log user out of the app and go to the login screen
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset SharedPrefs to get rid of stored username
                PreferenceManager.getDefaultSharedPreferences(GlobalState.getAppContext()).edit().clear().commit();

                // close websocket
                GlobalState appState = (GlobalState) getActivity().getApplicationContext();
                appState.closeWebsocket();

                // go to login screen
                i = new Intent(getActivity(), LoginScreen.class);
                startActivity(i);
            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(GlobalState.getAppContext());

        reportViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getActivity(), ReportViewingScreen.class);
                j.putExtra(Const.INTENT_PROFILE_VIEW_USERNAME, "testusername1");
                j.putExtra("reportId", 1);
                startActivity(j);
            }
        });

        // instantiate text views
        usernameView = view.findViewById(R.id.profileViewScreen_username);
        availabilityView = view.findViewById(R.id.myProfileViewScreen_availability);

        //TODO set text

        // instantiate image view
        profilePicView = view.findViewById(R.id.profileViewScreen_profilePic);
        Glide.with(GlobalState.getAppContext()).load(Const.URL_PROFILE_PICTURES + prefs.getString(Const.SHAREDPREFS_PROFILEPIC_KEY, Const.URL_DEFAULT_PROFILE_PIC)).into(profilePicView);

        usernameView.setText(prefs.getString(Const.SHAREDPREFS_USERNAME_KEY, "Username"));
        availabilityView.setText(prefs.getString(Const.SHAREDPREFS_AVAILABILITY_KEY, "Availability"));

        //TODO implement rest of profile view screen

        return view;
    }
}