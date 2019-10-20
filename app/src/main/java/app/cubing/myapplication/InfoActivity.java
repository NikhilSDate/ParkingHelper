package app.cubing.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class InfoActivity extends AppCompatActivity {
    BottomSheetBehavior aboutBehavior;
    BottomSheetBehavior privacyBehavior;
    BottomSheetBehavior disclaimerBehavior;
    BottomSheetBehavior copyrightBehavior;


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
    }
}
