package com.example.easyplan;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
public class HelpFragment extends Fragment {
    TextView faqView;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =  inflater.inflate(R.layout.fragment_help, container, false);
        faqView = (TextView) view.findViewById(R.id.faq_text);
        String faq_msg = getResources().getString(R.string.FAQ_msg);
        String faq_click_msg = getResources().getString(R.string.FAQ_msg_click);
        faqView.setText(faq_msg + faq_click_msg, TextView.BufferType.SPANNABLE);
        Spannable span = (Spannable) faqView.getText();
        int start = faq_msg.length();
        int end = start + faq_click_msg.length();
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.button_red)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.BOLD),start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                FAQ_fragment faq = new FAQ_fragment();
                FragmentTransaction faq_tr = getFragmentManager().beginTransaction();
                faq_tr.replace(R.id.fragment, faq, "FAQ_page");
                faq_tr.commit();
              //  Toast.makeText(getActivity(), "Works", Toast.LENGTH_LONG).show();
            }
        };

        span.setSpan(clickSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        faqView.setMovementMethod(LinkMovementMethod.getInstance());





        return view;
    }



    public void callKUSIS(View view) {

        Uri u = Uri.parse("tel:" + "+90 212 338 10 00");
        Intent i = new Intent(Intent.ACTION_DIAL, u);
        startActivity(i);
    }
}


