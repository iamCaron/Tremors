package com.example.android.quakereport;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EarthquakeDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    double latitude;
    double longitude;
    String location;
    String formattedDate;
    String formattedTime;
    Double mag;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_details);




        Bundle bundle=getIntent().getExtras();
        location=bundle.getString("location");
        long timeInMilliseconds=bundle.getLong("time");
        mag=bundle.getDouble("mag");
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(mag);
        url=bundle.getString("url");

        TextView magnitudeCircleView=(TextView)findViewById(R.id.perceived_magnitude);
        magnitudeCircleView.setText(formattedMagnitude);

        TextView secondaryLocationView=(TextView)findViewById(R.id.location_offset);
        TextView primaryLocationView=(TextView)findViewById(R.id.primary_location);
        if(location.contains("of")){
            String[] splittedLocation=location.split("of");
            secondaryLocationView.setText(splittedLocation[0]+" of ");
            primaryLocationView.setText(splittedLocation[1]);

        }else {
            secondaryLocationView.setText("Near the");
            primaryLocationView.setText(location);
        }



        TextView dateTextView=(TextView)findViewById(R.id.date);
        TextView timeTextView=(TextView)findViewById(R.id.time);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(timeInMilliseconds);



        // Format the date string (i.e. "Mar 3, 1984")
        formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateTextView.setText(formattedDate);


        // Format the time string (i.e. "4:30PM")
        formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeTextView.setText(formattedTime);

        // Set the proper background color on the magnitude circle.

        LinearLayout magnitudeCircleLayout=(LinearLayout)findViewById(R.id.magnitude_circle);
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeCircleLayout.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(mag);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        latitude=bundle.getDouble("latitude");
        longitude=bundle.getDouble("longitude");
        String depth=formatMagnitude(bundle.getDouble("depth"));

        TextView depthTextView=(TextView)findViewById(R.id.depth);
        depthTextView.setText(depth+" kms");

        TextView coordinatesTextView=(TextView) findViewById(R.id.coordinates);
        coordinatesTextView.setText(latitude+"° N,\n "+longitude+"° W");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            String shareBody ="A "+mag+" magnitude earthquake has occured "+location+" at "+formattedTime+" on "+formattedDate+"!\n"+" "+url;

            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }
        return false;


    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }


    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(this, magnitudeColorResourceId);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        Log.d("Latitude and Longitude"," lat "+latitude+" lon "+longitude);
        LatLng coordinates = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(coordinates)
                .title("Earthquake Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.earthquake_details_menu, menu);
        return true;
    }


}
