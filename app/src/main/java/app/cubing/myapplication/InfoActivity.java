/*Copyright 2021 Nikhil Date

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
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
import android.widget.TextView;

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
    TextView versionName;
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
        versionName=findViewById(R.id.version_name);

        versionName.setText("Version "+BuildConfig.VERSION_NAME);
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
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.contact_email)});
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.contact_email_subject));
                mailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.contact_email_body));
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
