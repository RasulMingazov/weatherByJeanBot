public class Model {
    private String name;
    private double temp;
    private Double humidity;
    private String icon;
    private String main;
    private double feelsTemp;
    private double wind;
    private int visibility;

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getFeelsTemp() {
        return feelsTemp; }

    public void setFeelsTemp(double feelsTemp) {
        this.feelsTemp = feelsTemp; }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setHum(Double hum) {
        this.humidity = hum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
