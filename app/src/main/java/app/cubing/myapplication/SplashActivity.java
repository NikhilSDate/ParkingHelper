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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import java.io.IOException;

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
                try {
                    DataHelper.getSingletonInstance().loadData(context);
                    PriceDataHelper.getSingletonInstance().loadData(context);
                    if(Utils.DEV_BUILD) {
                        Log.i("TAG", DataHelper.getSingletonInstance().getBESTParkingSpacesList().toString());
                        Log.i("TAG", PriceDataHelper.getSingletonInstance().getPriceTable().toString());
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }



                Intent intent=new Intent(context,MapsActivity.class);
                context.startActivity(intent);

                SplashActivity.this.finish();
            }
        },250);
    }
}