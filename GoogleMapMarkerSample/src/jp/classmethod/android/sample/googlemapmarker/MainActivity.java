package jp.classmethod.android.sample.googlemapmarker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * GoogleMap を表示する Activity.
 */
public class MainActivity extends FragmentActivity {

    /** GoogleMap インスタンス. */
    private GoogleMap mMap;

    /** マーカー. */
    private Marker mMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Map を必要な場合のみセットアップする.
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            FragmentManager manager = (FragmentManager) getSupportFragmentManager();
            SupportMapFragment frag = (SupportMapFragment) manager.findFragmentById(R.id.map);
            mMap = frag.getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * Map をセットアップする.
     */
    private void setUpMap() {

        // マーカーを貼る緯度・経度
        LatLng location = new LatLng(35.697261, 139.774728);
        
        // マーカーの設定
        MarkerOptions options = new MarkerOptions();
        options.position(location);
        options.title("クラスメソッド株式会社");
        options.snippet(location.toString());
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_logo);
        options.icon(icon);
        
        // マップにマーカーを追加
        mMarker = mMap.addMarker(options);
        
//        mMap.setInfoWindowAdapter(new CustomInfoAdapter());
    }

    /**
     * InfoAdapter のカスタムクラス.
     */
    private class CustomInfoAdapter implements InfoWindowAdapter {
    
        /** Window の View. */
        private final View mWindow;
    
        /**
         * コンストラクタ.
         */
        public CustomInfoAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }
    
        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }
    
        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    
        /**
         * InfoWindow を表示する.
         * @param marker {@link Marker}
         * @param view {@link View}
         */
        private void render(Marker marker, View view) {
            // ここでどの Marker がタップされたか判別する
            if (marker.equals(mMarker)) {
                // 画像
                ImageView badge = (ImageView) view.findViewById(R.id.badge);
                badge.setImageResource(R.drawable.ic_logo);
            }
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView snippet = (TextView) view.findViewById(R.id.snippet);
            title.setText(marker.getTitle());
            snippet.setText(marker.getSnippet());
        }
    
    }

}
