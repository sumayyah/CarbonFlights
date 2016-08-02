package edu.davis.carbonflights.transport.car;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.Transportation;

/**
 * Created by Sumayyah on 9/13/13.
 */
public class Car extends Transportation {

    public static final int Type = 4;

    public static final String flags = "0x0000EFFF";

    public Car() {
        hops = new ArrayList<CarHop>();
    }

    public Car(Parcel in) {
        super(in);
        in.readTypedList((ArrayList<CarHop>)hops, CarHop.CREATOR);
    }

    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>() {
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
}
