package sistemas.ing.gironza.notasunicorappultimate;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class AlarmReciever extends BroadcastReceiver {

    Context context2;
    @Override
    public void onReceive(Context context, Intent intent)
    {   //Build pending intent2 from calling information to display Notification

        context2=context;
        Bundle ext= intent.getExtras();

        String nombre=ext.getString("Nombre"," ");
        String lugar=ext.getString("Lugar"," ");
        int Retrazo=ext.getInt("Retrazo");

        createNotificationChannel();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);

        Intent intent2 = new Intent(context, MainActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);

        NotificationCompat.Builder mnotBuilder  = new NotificationCompat.Builder(context,"NUAPP")
                .setSmallIcon(R.drawable.notificacionnuap)
                .setColor(context.getResources().getColor(R.color.colorVerdeApp))
                .setSubText("Salón: "+lugar)
                .setBadgeIconType(R.drawable.notificacionnuap)
                .setContentTitle("Notas UnicorApp")
                .setVibrate(new long[] { 1000, 1000, 3300, 1000, 1000 })
                .setSound(notification)
                .setPriority(Notification.PRIORITY_HIGH)
                .setLights(Color.GREEN, 1, 0)
                .setTicker("Hora de clases")
                .setAutoCancel(true)
                .setContentText("Clases en "+Retrazo+" min de "+nombre);
        mnotBuilder.setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, mnotBuilder.build());

        r.play();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NUAPP", "Notas UnicorApp", importance);
            channel.setDescription("Canal para la app Notas UnicorApp ");

            NotificationManager notificationManager = context2.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

}