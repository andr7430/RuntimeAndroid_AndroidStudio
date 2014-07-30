package com.androiddemo.app;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.tasks.tilecache.ExportTileCacheParameters;
import com.esri.core.tasks.tilecache.ExportTileCacheTask;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class MainActivity extends ActionBarActivity {


    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the map and initial extent from XML layout
        mMapView = (MapView)findViewById(R.id.map);
        // Add dynamic layer to MapView
        ArcGISTiledMapServiceLayer tiledlayer = new ArcGISTiledMapServiceLayer("" +
                "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
        mMapView.addLayer(tiledlayer);

        mMapView.setOnSingleTapListener(new OnSingleTapListener() {
            @Override
            public void onSingleTap(float v, float v2) {
                mMapView.zoomin();
                //downloadBasemap();
            }
        });

//        // Add dynamic layer to MapView
//        ArcGISDynamicMapServiceLayer alayer = new ArcGISDynamicMapServiceLayer("" +
//                "http://sampleserver6.arcgisonline.com/arcgis/rest/services/Census/MapServer");
//        alayer.setMinScale(1155581);
//        mMapView.addLayer(alayer);
    }

    public void downloadBasemap(){
        Envelope extentForTPK = new Envelope();
        mMapView.getExtent().queryEnvelope(extentForTPK);
        double[] levels = {0,1,2,3,4};

        String defaultPath = Environment.getExternalStorageDirectory().getPath()
                + "/ArcGIS/samples/tiledcache/";

        final String tileCachePath = defaultPath;


        final ExportTileCacheTask exportTileCacheTask = new ExportTileCacheTask("http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer", null);

        // Set up GenerateTileCacheParameters
        ExportTileCacheParameters params = new ExportTileCacheParameters(
                true, levels, ExportTileCacheParameters.ExportBy.ID, extentForTPK,
                mMapView.getSpatialReference());

        // create tile cache
        //createTileCache(params, exportTileCacheTask, tileCachePath);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
