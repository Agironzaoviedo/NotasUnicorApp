package sistemas.ing.gironza.notasunicorappultimate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IniciadorServicio extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent servicio = new Intent(context,  BackgroundService.class);
        context.startService(servicio);

    }
}
