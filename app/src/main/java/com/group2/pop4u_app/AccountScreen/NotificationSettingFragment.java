package com.group2.pop4u_app.AccountScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentNotificationSettingBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationSettingFragment extends Fragment {

    FragmentNotificationSettingBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationSettingFragment newInstance(String param1, String param2) {
        NotificationSettingFragment fragment = new NotificationSettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationSettingBinding.inflate(inflater, container, false);
        setupSwitchListeners();
        return binding.getRoot();
    }

    private void setupSwitchListeners() {
        // Listener for in-app notification switch
        binding.switchInAppNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the state to SharedPreferences or handle the change
            saveNotificationSetting("in_app_notifications", isChecked);
        });

        // Listener for email notification switch
        binding.switchEmailNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the state to SharedPreferences or handle the change
            saveNotificationSetting("email_notifications", isChecked);
        });

        // Load initial state from SharedPreferences or your storage mechanism
        loadInitialSwitchStates();
    }

    private void saveNotificationSetting(String key, boolean value) {
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void loadInitialSwitchStates() {
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean inAppNotifEnabled = prefs.getBoolean("in_app_notifications", false);
        boolean emailNotifEnabled = prefs.getBoolean("email_notifications", false);
        binding.switchInAppNotifications.setChecked(inAppNotifEnabled);
        binding.switchEmailNotifications.setChecked(emailNotifEnabled);
    }
}