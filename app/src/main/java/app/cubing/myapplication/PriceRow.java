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

import android.util.Log;

import java.util.Objects;

public class PriceRow {
    public static final int TIME_UPTO_1=0;
    public static final int TIME_1TO3=1;
    public static final int TIME_3TO6=2;
    public static final int TIME_6TO12=3;
    public static final int TIME_MORETHAN12=4;
    public static final int TIME_MONTHLY_DAY=5;
    public static final int TIME_MONTHLY_NIGHT=6;


    public static final int VEHICLE_34W=7;
    public static final int VEHICLE_2W=8;
    public static final int VEHICLE_TRUCK=9;
    public static final int VEHICLE_AUTOTAXI=10;
    public static final int VEHICLE_PT=11;

    public static final int PRICE_NA=-1;


    private char priceCategory;
    private int timeCategory;
    private int vehicle;
    public PriceRow(char priceCategory, int timeCategory, int vehicle){
        this.priceCategory =priceCategory;
        this.timeCategory=timeCategory;
        this.vehicle=vehicle;
    }

    public char getCategory() {
        return priceCategory;
    }

    public int getTimeCategory() {
        return timeCategory;
    }

    public int getVehicle() {
        return vehicle;
    }



    @Override
    public boolean equals(Object object){
        if(object instanceof PriceRow){
            PriceRow castedObject=(PriceRow)object;
            boolean equals=(priceCategory==castedObject.getCategory())&&
                    (timeCategory==castedObject.getTimeCategory())&&
                    (vehicle==castedObject.getVehicle());

            return equals;
        }else{
            return false;
        }

    }
    @Override
    public int hashCode(){
        return Objects.hash(this.priceCategory,this.timeCategory,this.vehicle);

    }

    @Override
    public String toString() {
        return "PriceRow{" +
                "priceCategory=" + priceCategory +
                ", timeCategory=" + timeCategory +
                ", vehicle=" + vehicle +
                '}';
    }
}
