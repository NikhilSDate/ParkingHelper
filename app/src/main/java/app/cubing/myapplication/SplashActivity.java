package app.cubing.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Context context=SplashActivity.this;
                DataHelper.getSingletonInstance().loadData(context);
                Log.i("TAG", DataHelper.getSingletonInstance().getBESTParkingSpacesList().toString());
                Intent intent=new Intent(context,MapsActivity.class);
                context.startActivity(intent);

                SplashActivity.this.finish();
            }
        },250);
    }
}
