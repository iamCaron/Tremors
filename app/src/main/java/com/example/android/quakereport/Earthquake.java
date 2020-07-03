package com.example.android.quakereport;

public class Earthquake {

    private double mMagnitude;

    private double mLatitude;
    private double mLongitude;
    private double mDepth;

    private String mLocation;

    /** Time of the earthquake */
    private long mTimeInMilliseconds;


    private String mUrl;


    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url, double latitude, double longitude, double depth){
        mMagnitude=magnitude;
        mLocation=location;
        mTimeInMilliseconds=timeInMilliseconds;
        mUrl=url;
        mLatitude=latitude;
        mLongitude=longitude;
        mDepth=depth;
    }

    public double getMagnitude() {
        return mMagnitude;
    }



    public String getLocation() {
        return mLocation;
    }



    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }


    public String getUrl() {
        return mUrl;
    }


    public double getmMagnitude() {
        return mMagnitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public double getmDepth() {
        return mDepth;
    }

    public String getmLocation() {
        return mLocation;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getmUrl() {
        return mUrl;
    }

}
