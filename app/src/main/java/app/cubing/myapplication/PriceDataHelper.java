package app.cubing.myapplication;

import java.util.ArrayList;

public class PriceDataHelper {
    public static final int TIME_UPTO_1=0;
    public static final int TIME_1TO3=0;
    public static final int TIME_3TO6=0;
    public static final int TIME_6TO12=0;
    public static final int TIME_MORETHAN12=0;
    public static final int TIME_MONTHLY_DAY=0;
    public static final int TIME_MONTHLY_NIGHT=0;


    public static final int VEHICLE_34W=0;
    public static final int VEHICLE_2W=0;
    public static final int VEHICLE_TRUCK=0;
    public static final int VWHICLE_AUTOTAXI=0;
    public static final int PT=0;





    ArrayList<int[]> priceTable=new ArrayList<>();
    private static PriceDataHelper priceDataHelper=new PriceDataHelper();

    private PriceDataHelper(){

    }

}
