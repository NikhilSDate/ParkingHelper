package app.cubing.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;

public class InfoActivity extends AppCompatActivity {
    BottomSheetBehavior aboutBehavior;
    BottomSheetBehavior privacyBehavior;
    BottomSheetBehavior disclaimerBehavior;
    BottomSheetBehavior copyrightBehavior;
    MaterialButton aboutButton;
    MaterialButton privacyButton;
    MaterialButton disclaimerButton;
    MaterialButton copyrightButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ConstraintLayout aboutLayout=findViewById(R.id.about_bottom_sheet);
        ConstraintLayout privacyLayout=findViewById(R.id.privacy_bottom_sheet);
        ConstraintLayout disclaimerLayout=findViewById(R.id.disclaimer_bottom_sheet);
        ConstraintLayout copyrightLayout=findViewById(R.id.copyright_bottom_sheet);

        aboutBehavior=BottomSheetBehavior.from(aboutLayout);
        privacyBehavior=BottomSheetBehavior.from(privacyLayout);
        disclaimerBehavior=BottomSheetBehavior.from(disclaimerLayout);
        copyrightBehavior=BottomSheetBehavior.from(copyrightLayout);

        aboutButton=findViewById(R.id.about_button);
        privacyButton=findViewById(R.id.privacy_button);
        disclaimerButton=findViewById(R.id.disclaimer_button);
        copyrightButton=findViewById(R.id.copyright_button);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                copyrightBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        privacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                copyrightBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        disclaimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                copyrightBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        copyrightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                privacyBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                disclaimerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                copyrightBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

    }
}
