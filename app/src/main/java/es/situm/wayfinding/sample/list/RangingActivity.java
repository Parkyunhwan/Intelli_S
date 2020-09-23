package es.situm.wayfinding.sample.list;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.Edits;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import es.situm.wayfinding.sample.R;

public class RangingActivity extends AppCompatActivity  implements BeaconConsumer {
    protected static final String TAG1 = "::MonitoringActivity::";
    protected static final String TAG2 = "::RangingActivity::";
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        //getBeaconParsers() = 활성 비콘 파서 목록을 가져옴
        //거기에 새로운 비콘파서 형식을 만들어서 .add()
        //setBeaconLayout(String) = BLE 알림 내에서 0으로 색인화 된 오프셋을 바이트로 지정하는 문자열을 기반으로 비콘 필드 구문 분석 알고리즘을 정의
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        //Android Activity또는 Service에 바인딩 합니다 BeaconService.
        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Android Activity또는 Service에 바인딩 을 해제. BeaconService.
        beaconManager.unbind(this);
    }
    @Override
    //비콘 서비스가 실행 중이고 BeaconManager를 통해 명령을 수락 할 준비가되면 호출.
    public void onBeaconServiceConnect() {
        //모든 범위 알리미를 제거한다.
        beaconManager.removeAllRangeNotifiers();
        //BeaconService비콘 영역을 보거나 멈출 때 마다 호출해야하는 클래스를 지정.
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            //하나 이상의 비콘 Region이 표시 될 때 호출
            public void didEnterRegion(Region region) {
                Log.i(TAG1, ":최소하나의 비콘 발견:");
            }
            @Override
            //비콘 Region이 보이지 않을 때 호출
            public void didExitRegion(Region region) {
                Log.i(TAG1, ":비콘을 찾을 수 없음:");
            }
            @Override
            //하나 이상의 비콘 Region이 표시 될 때 MonitorNotifier.INSIDE 상태 값으로 호출
            public void didDetermineStateForRegion(int state, Region region) {
                if(state==0){
                    Log.i(TAG1, ":::::비콘이 보이는 상태이다. state : "+state + ":::::");
                } else {
                    Log.i(TAG1, ":::::비콘이 보이지 않는 상태이다. state : "+state +":::::");
                }
            }
        });

        //범위한정 알림을 추가한다
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            //눈에 보이는 비콘에 대한 mDistance(major 또는 minor와의 거리를 뜻하는)의 추정치를 제공하기 위해 초당 한 번 호출
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                List<Beacon> list = (List<Beacon>)beacons;
                if (beacons.size() > 0) {
                    for (Beacon B : list) {
                        //Log.i(TAG2, "Beacon : " + B + ":::");
                        Log.i(TAG2, ":::::The beacon Distance is about " + B.getDistance() + " meters away.:::::");
                        Log.i(TAG2, ":::::This :: U U I D :: of beacon   :  " + B.getId1().toString() + ":::::");
                        Log.i(TAG2, ":::::This ::M a j o r:: of beacon   :  " + B.getId2().toString() + ":::::");
                        Log.i(TAG2, ":::::This ::M i n o r:: of beacon   :  " + B.getId3().toString() + ":::::");

                        // 통신 코드
                        // 문 개폐 일정거리 정해놓고 서버로 보냄


//                        Log.i(TAG2, ":::::The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.:::::");
//                        Log.i(TAG2, ":::::This :: U U I D :: of beacon   :  " + beacons.iterator().next().getId1().toString() + ":::::");
//                        Log.i(TAG2, ":::::This ::M a j o r:: of beacon   :  " + beacons.iterator().next().getId2().toString() + ":::::");
//                        Log.i(TAG2, ":::::This ::M i n o r:: of beacon   :  " + beacons.iterator().next().getId3().toString() + ":::::");
                    }
                }
            }
        });
        try {
            //알려주는 BeaconService전달 일치 비콘을 찾고 시작하는 Region개체를 지역에서 비콘을 볼 수있는 동안 추정 mDistance에있는 모든 초 업데이트를 제공
            beaconManager.startRangingBeaconsInRegion(new Region("C2:02:DD:00:13:DD", null, null, null));
        } catch (RemoteException e) {    }
    }
}