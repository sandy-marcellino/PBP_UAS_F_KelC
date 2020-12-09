package com.pbpkelompok3.shopping.ui;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.pbpkelompok3.shopping.LoginActivity;
import com.pbpkelompok3.shopping.R;

public class LogoutFragment extends DialogFragment {

    private SharedPreferences preferences;
    private String CHANNEL_ID = "Channel logout";
    MaterialButton btnCancel,btnYes;

//    public static LogoutFragment newInstance(){ return new LogoutFragment();}


    public LogoutFragment()
    {
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_logout,new LinearLayout(getActivity()),false);

        btnCancel = v.findViewById(R.id.btn_cancel_logout);
        btnYes = v.findViewById(R.id.btn_yes_Logout);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email","");
                editor.putInt("isLogin",0);
                editor.commit();
                createNotificationChannel();
                addNotification();
                Intent intent= new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(v);
        return builder;
    }

    private void createNotificationChannel() {
        // NotificationChannel diperlukan untuk API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel logout";
            String description = "This is Channel logout";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification() {
        //konstruktor NotificationCompat.Builder harus diberi CHANNEL_ID untuk API level 26+
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Good bye ")
                .setContentText("Hope you have a nice day, see you soon !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Membuat intent yang menampilkan notifikasi
        Intent notificationIntent = new Intent(getActivity(), LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivities(getActivity(),0, new Intent[]{notificationIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Menampilkan notifikasi
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
}