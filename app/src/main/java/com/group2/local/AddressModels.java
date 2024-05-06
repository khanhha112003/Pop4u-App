package com.group2.local;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class AddressModels {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CityRequestModel {
        @JsonProperty("results")
        public Collection<CityItem> result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CityItem {
        @JsonProperty("province_name")
        public String name;
        @JsonProperty("province_id")
        public String code;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DistrictRequestModel {
        @JsonProperty("results")
        public Collection<DistrictItem> result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DistrictItem {
        @JsonProperty("district_name")
        public String name;
        @JsonProperty("district_id")
        public String code;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WardRequestModel {
        @JsonProperty("results")
        public Collection<WardItem> result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WardItem {
        @JsonProperty("ward_name")
        public String name;
        @JsonProperty("ward_id")
        public String code;
    }
}
