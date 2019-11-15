package app.cubing.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;

public class InfoActivity extends AppCompatActivity {
    BottomSheetBehavior aboutBehavior;
    BottomSheetBehavior privacyBehavior;
    BottomSheetBehavior disclaimerBehavior;
    BottomSheetBehavior helpBehavior;
    BottomSheetBehavior dataBehavior;
    MaterialButton aboutButton;
    MaterialButton privacyButton;
    MaterialButton disclaimerButton;
    MaterialButton helpButton;
    MaterialButton dataButton;
    MaterialButton contactButton;
    SwitchCompat busLotsSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ConstraintLayout aboutLayout=findViewById(R.id.about_bottom_sheet);
        ConstraintLayout privacyLayout=findViewById(R.id.privacy_bottom_sheet);
        ConstraintLayout disclaimerLayout=findViewById(R.id.disclaimer_bottom_sheet);
        ConstraintLayout helpLayout=findViewById(R.id.help_bottom_sheet);
        ConstraintLayout dataLayout=findViewById(R.id.data_bottom_sheet);
        busLotsSwitch=findViewById(R.id.bus_lots_switch);
        aboutBehavior=BottomSheetBehavior.from(aboutLayout);
        privacyBehavior=BottomSheetBehavior.from(privacyLayout);
        disclaimerBehavior=BottomSheetBehavior.from(disclaimerLayout);
        helpBehavior=BottomSheetBehavior.from(helpLayout);
        dataBehavior=BottomSheetBehavior.from(dataLayout);


        aboutButton=findViewById(R.id.about_button);
        privacyButton=findViewById(R.id.privacy_button);
        disclaimerButton=findViewById(R.id.disclaimer_button);
        helpButton =findViewById(R.id.help_button);
        dataButton=findViewById(R.id.data_button);
        contactButton=findViewById(R.id.contact_button);
        SharedPreferences preferences=getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean showBusLots=preferences.getBoolean("showBusLots", true);
        busLotsSwitch.setChecked(showBusLots);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                helpBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                dataBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        privacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                helpBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                dataBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        disclaimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                helpBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                dataBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                helpBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                dataBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                helpBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                dataBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent=new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:"));
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mumbaiparkinghelper@gmail.com"});
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "I have come across the following public parking lot which I think is not shown in your app:");
                mailIntent.putExtra(Intent.EXTRA_TEXT, "Address of parking lot");
                startActivity(mailIntent);

            }
        });
        busLotsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Context context=InfoActivity.this;
                    SharedPreferences preferences=context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("showBusLots", true);
                    editor.apply();

                }else{
                    Context context=InfoActivity.this;
                    SharedPreferences preferences=context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("showBusLots", false);
                    editor.apply();

                }
            }
        });

    }
}
