package app.cubing.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PriceDataHelper {
    private ArrayList<PriceRow> priceTable=new ArrayList<>();
    private static PriceDataHelper priceDataHelper=new PriceDataHelper();

    private PriceDataHelper(){

    }
    public static PriceDataHelper getSingletonInstance(){
        return priceDataHelper;
    }
    public void loadData(Context context){
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.price_table);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(reader);
            String line;
            while ((line = bf.readLine()) != null){

            }
        }catch (IOException e){

        }
    }

}
