package com.example.easyplan;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.format.TextStyle;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment implements View.OnClickListener{
    CardView faqView, contactUs, kusisMail, regCall;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =  inflater.inflate(R.layout.fragment_help, container, false);
        faqView = view.findViewById(R.id.faq_text);
        faqView.setOnClickListener(this);
        contactUs = view.findViewById(R.id.contactUS);
        contactUs.setOnClickListener(this);
        kusisMail = view.findViewById(R.id.kusismail);
        kusisMail.setOnClickListener(this);
        regCall = view.findViewById(R.id.registeationCall);
        regCall.setOnClickListener(this);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.faq_text:
                FAQ_fragment faq = new FAQ_fragment();
                FragmentTransaction faq_tr = getFragmentManager().beginTransaction();
                faq_tr.replace(R.id.fragment, faq, "FAQ_page");
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                faq_tr.commit();
                break;
            case R.id.contactUS:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, getResources().getString(R.string.contactUsEmail));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Easy Plan");
                intent.putExtra(Intent.EXTRA_TEXT, "Hello Easy Plan Team,\n");
                intent.setData(Uri.parse("mailto:" + getResources().getString(R.string.contactUsEmail)));
                startActivity(Intent.createChooser(intent, "Send e-mail to"));
                break;
            case R.id.kusismail:
                Intent intent2 = new Intent(Intent.ACTION_SENDTO);
                intent2.setType("message/rfc822");
                intent2.putExtra(Intent.EXTRA_EMAIL, getResources().getString(R.string.kusis_help_email));
                intent2.setData(Uri.parse("mailto:" + getResources().getString(R.string.kusis_help_email)));
                startActivity(Intent.createChooser(intent2, "Send e-mail to"));
                break;
            case R.id.registeationCall:
                Uri phone = Uri.parse(getResources().getString(R.string.registration_phone));
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phone);
                startActivity(callIntent);

        }

    }

}


