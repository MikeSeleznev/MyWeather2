package com.example.myweather.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {

        @SerializedName("last_updated_epoch")
        @Expose
        private Integer last_updated_epoch;
        @SerializedName("last_updated")
        @Expose
        private String last_updated;
        @SerializedName("temp_c")
        @Expose
        private Double temp_c;
        @SerializedName("temp_f")
        @Expose
        private Double temp_f;
        @SerializedName("is_day")
        @Expose
        private Integer is_day;
        @SerializedName("condition")
        @Expose
        private Condition condition;
        @SerializedName("wind_mph")
        @Expose
        private Double wind_mph;
        @SerializedName("wind_kph")
        @Expose
        private Double windKph;
        @SerializedName("wind_degree")
        @Expose
        private Integer wind_degree;
        @SerializedName("wind_dir")
        @Expose
        private String wind_dir;
        @SerializedName("pressure_mb")
        @Expose
        private Double pressure_mb;
        @SerializedName("pressure_in")
        @Expose
        private Double pressureIn;
        @SerializedName("precip_mm")
        @Expose
        private Double precipMm;
        @SerializedName("precip_in")
        @Expose
        private Double precipIn;
        @SerializedName("humidity")
        @Expose
        private Integer humidity;
        @SerializedName("cloud")
        @Expose
        private Integer cloud;
        @SerializedName("feelslike_c")
        @Expose
        private Double feelslike_c;
        @SerializedName("feelslike_f")
        @Expose
        private Double feelslikeF;
        @SerializedName("vis_km")
        @Expose
        private Double visKm;
        @SerializedName("vis_miles")
        @Expose
        private Double visMiles;
        @SerializedName("uv")
        @Expose
        private Double uv;

        public Integer getLastUpdatedEpoch() {
            return last_updated_epoch;
        }

        public void setLastUpdatedEpoch(Integer lastUpdatedEpoch) {
            this.last_updated_epoch = lastUpdatedEpoch;
        }

        public String getLastUpdated() {
            return last_updated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.last_updated = lastUpdated;
        }

        public Double getTempC() {
            return temp_c;
        }

        public void setTempC(Double tempC) {
            this.temp_c = tempC;
        }

        public Double getTempF() {
            return temp_f;
        }

        public void setTempF(Double tempF) {
            this.temp_f = tempF;
        }

        public Integer getIsDay() {
            return is_day;
        }

        public void setIsDay(Integer isDay) {
            this.is_day = isDay;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public Double getWindMph() {
            return wind_mph;
        }

        public void setWindMph(Double windMph) {
            this.wind_mph = windMph;
        }

        public Double getWindKph() {
            return windKph;
        }

        public void setWindKph(Double windKph) {
            this.windKph = windKph;
        }

        public Integer getWindDegree() {
            return wind_degree;
        }

        public void setWindDegree(Integer windDegree) {
            this.wind_degree = windDegree;
        }

        public String getWindDir() {
            return wind_dir;
        }

        public void setWindDir(String windDir) {
            this.wind_dir = windDir;
        }

        public Double getPressureMb() {
            return pressure_mb;
        }

        public void setPressureMb(Double pressureMb) {
            this.pressure_mb = pressureMb;
        }

        public Double getPressureIn() {
            return pressureIn;
        }

        public void setPressureIn(Double pressureIn) {
            this.pressureIn = pressureIn;
        }

        public Double getPrecipMm() {
            return precipMm;
        }

        public void setPrecipMm(Double precipMm) {
            this.precipMm = precipMm;
        }

        public Double getPrecipIn() {
            return precipIn;
        }

        public void setPrecipIn(Double precipIn) {
            this.precipIn = precipIn;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Integer getCloud() {
            return cloud;
        }

        public void setCloud(Integer cloud) {
            this.cloud = cloud;
        }

        public Double getFeelslikeC() {
            return feelslike_c;
        }

        public void setFeelslikeC(Double feelslikeC) {
            this.feelslike_c = feelslikeC;
        }

        public Double getFeelslikeF() {
            return feelslikeF;
        }

        public void setFeelslikeF(Double feelslikeF) {
            this.feelslikeF = feelslikeF;
        }

        public Double getVisKm() {
            return visKm;
        }

        public void setVisKm(Double visKm) {
            this.visKm = visKm;
        }

        public Double getVisMiles() {
            return visMiles;
        }

        public void setVisMiles(Double visMiles) {
            this.visMiles = visMiles;
        }

        public Double getUv() {
            return uv;
        }

        public void setUv(Double uv) {
            this.uv = uv;
        }

}
