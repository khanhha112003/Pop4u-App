package com.group2.local;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class AddressHelper {

    public static final AddressHelper instance = new AddressHelper();

    interface AddressService {
        @GET("province/")
        Call<AddressModels.CityRequestModel> getListProvince();

        @GET("province/district/{province_id}")
        Call<AddressModels.DistrictRequestModel> getListDistrict(@Path("province_id") String province_id);

        @GET("province/ward/{district_id}")
        Call<AddressModels.WardRequestModel> getListWard(@Path("district_id") String district_id);
    }

    // fetch list city from https://vapi.vnappmob.com/api/province/
    // return list district from https://vapi.vnappmob.com/api/province/district/{}
    // return list ward from https://vapi.vnappmob.com/api/province/ward/{}
    Retrofit.Builder builder;

    AddressService service;
    final ExecutorService executor = Executors.newSingleThreadExecutor();
    ArrayList<HashMap<String, String>> listCity = new ArrayList<>();
    ArrayList<HashMap<String, String>> listDistrict = new ArrayList<>();

    AddressHelperGetData listener;

    public void init() {
        syncListCity();
    }

    public void setAddressHelperListener(AddressHelperGetData listener) {
        this.listener = listener;
    }

    AddressHelper() {
        builder = new Retrofit.Builder()
                .baseUrl("https://vapi.vnappmob.com/api/")
                .addConverterFactory(JacksonConverterFactory.create());
        service = builder.build().create(AddressService.class);
        listCity.add(new HashMap<String, String>() {{
            put("name", "");
            put("code", "00");
        }});
    }

    void syncListCity() {
        executor.execute(() -> {
            Call<AddressModels.CityRequestModel> call = service.getListProvince();
            try {
                AddressModels.CityRequestModel response = call.execute().body();
                if (response != null) {
                    listCity = new ArrayList<>();
                    for (AddressModels.CityItem item : response.result) {
                        HashMap<String, String> city = new HashMap<>();
                        city.put("name", item.name);
                        city.put("code", item.code);
                        listCity.add(city);
                    }
                } else {
                    Log.d("AddressHelper", "Response is null");
                }
            } catch (Exception e) {
                Log.d("AddressHelper", Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    public ArrayList<HashMap<String, String>> getListCity() {
        return listCity;
    }

    public void getListDictrict(String city_id) {
        executor.execute(() -> {
            Call<AddressModels.DistrictRequestModel> call = service.getListDistrict(city_id);
            try {
                Response<AddressModels.DistrictRequestModel> response = call.execute();
                if (response.body() != null) {
                    listDistrict = new ArrayList<>();
                    for (AddressModels.DistrictItem item : response.body().result) {
                        HashMap<String, String> district = new HashMap<>();
                        district.put("name", item.name);
                        district.put("code", item.code);
                        listDistrict.add(district);
                    }
                    if (listener != null) {
                        listener.onGetDataDistrict(listDistrict);
                    }
                    return;
                } else {
                    Log.d("AddressHelper", "Response is null");
                    if (listener != null) {
                        listener.onGetDataDistrict(new ArrayList<>());
                    }
                    return;
                }
            } catch (Exception e) {
                Log.d("AddressHelper", Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    public void getListWard(String district_id) {
        ArrayList<HashMap<String, String>> listWard = new ArrayList<>();
        executor.execute(() -> {
            Call<AddressModels.WardRequestModel> call = service.getListWard(district_id);
            try {
                AddressModels.WardRequestModel response = call.execute().body();
                if (response != null) {
                    for (AddressModels.WardItem item : response.result) {
                        HashMap<String, String> ward = new HashMap<>();
                        ward.put("name", item.name);
                        ward.put("code", item.code);
                        listWard.add(ward);
                    }
                    if (listener != null) {
                        listener.onGetDataWard(listWard);
                    }
                } else {
                    Log.d("AddressHelper", "Response is null");
                    if (listener != null) {
                        listener.onGetDataWard(new ArrayList<>());
                    }
                }
            } catch (Exception e) {
                Log.d("AddressHelper", Objects.requireNonNull(e.getMessage()));
                if (listener != null) {
                    listener.onGetDataWard(new ArrayList<>());
                }
            }
        });
    }

    public interface AddressHelperGetData {
        void onGetDataDistrict(ArrayList<HashMap<String, String>> data);
        void onGetDataWard(ArrayList<HashMap<String, String>> data);
    }
}
