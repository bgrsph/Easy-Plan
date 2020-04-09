package com.example.easyplan;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{
    Button logout;
    FirebaseAuth auth;
    TextView personalInfo, notif, rate, share, terms;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        personalInfo = v.findViewById(R.id.PersonalInfoText);
        personalInfo.setOnClickListener(this);
        notif = v.findViewById(R.id.NotificationsText);
        notif.setOnClickListener(this);
        rate = v.findViewById(R.id.RateText);
        rate.setOnClickListener(this);
        share = v.findViewById(R.id.ShareText);
        share.setOnClickListener(this);
        terms = v.findViewById(R.id.TermsText);
        terms.setOnClickListener(this);
        logout = (Button) v.findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("remember");
                editor.commit();
                getActivity().finish();

                startActivity(new Intent(getActivity(), WelcomePage.class));
            }
        });

        return  v;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.PersonalInfoText:
                PersonalInfo pers = new PersonalInfo();
                FragmentTransaction faq_tr = getFragmentManager().beginTransaction();
                faq_tr.replace(R.id.fragment, pers, "Personal_Info");
                faq_tr.commit();
                break;
            case R.id.NotificationsText:
                /*** TO DO: App is not found error ***/
                Intent intent = new Intent();
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

                //for Android 5-7
                intent.putExtra("app_package", getContext().getPackageName());
                intent.putExtra("app_uid", getContext().getApplicationInfo().uid);

                // for Android 8 and above
                intent.putExtra("android.provider.extra.APP_PACKAGE",getContext().getPackageName());

                startActivity(intent);
                break;
            case R.id.RateText:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getContext().getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
                }
            case R.id.ShareText:
                Intent a = new Intent(Intent.ACTION_SEND);
                final String appPackageName = getContext().getApplicationContext().getPackageName();
                String strAppLink = "";
                try
                {
                    strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                }
                catch (android.content.ActivityNotFoundException anfe)
                {
                    strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                }
                a.setType("text/link");
                String shareBody = "Easy Plan App: " +
                        "\n"+""+strAppLink;
                a.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(a, "Share Via"));
            case R.id.TermsText:
                TermsAndServices terms = new TermsAndServices();
                FragmentTransaction terms_tr = getFragmentManager().beginTransaction();
                terms_tr.replace(R.id.fragment, terms, "TermsAndServices");
                terms_tr.commit();
                break;

        }

    }


    }
