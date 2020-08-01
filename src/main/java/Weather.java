import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {
        URL url1 = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=");

        Scanner inp = new Scanner((InputStream) url1.getContent());
        String result = "";
        while (inp.hasNext()) {
            result += inp.nextLine();
        }

        JSONObject object = new JSONObject(result);
        String nameOfCity = message.substring(0, 1).toUpperCase() + message.substring(1).toLowerCase();
        model.setName(toUpperCaseForFirstLetter(nameOfCity));


        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));
        model.setFeelsTemp(main.getDouble("feels_like"));

        JSONObject wind = object.getJSONObject("wind");
        model.setWind(wind.getDouble("speed"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);

            switch ((String)obj.get("main")) {
                case "Thunderstorm": model.setMain("Гроза\u26c8");
                case "Drizzle": model.setMain("Морось\ud83c\udf27");
                case "Rain": model.setMain("Дождь\u2614\ufe0f");
                case "Snow": model.setMain("Снег\u2744\ufe0f");
                case "Mist": model.setMain("Легкий туман\ud83c\udf2b");
                case "Smoke": model.setMain("Туман\ud83c\udf2b");
                case "Haze": model.setMain("Туман\ud83c\udf2b");
                case "Dust": model.setMain("Пыль");
                case "Fog": model.setMain("Туман\ud83c\udf2b");
                case "Tornado": model.setMain("Торнадо");
                case  "Squall": model.setMain("Шквал");
                case "Ash": model.setMain("Пепел");
                case "Clear": model.setMain("Чистое небо\u2600\ufe0f");
                case "Clouds": model.setMain("Облачно\u2601\ufe0f");
            }
        }
        //Оказалось, что для некоторых городов видимость не передается.
        // model.setVisibility(object.getInt("visibility"));
        String emoji = "";

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 1 && hour < 10)
            emoji = "\uD83C\uDF03";
        else emoji = "\uD83C\uDFD9";

        return
                "Город: " + model.getName() + emoji + "\n"
                        + "Температура: " + model.getTemp() + "℃" + "\n"
                        + "Ощущается как: " + model.getFeelsTemp() + "℃" + "\n"
                        + "Описание: " + model.getMain() + "\n"
                        + "Влажность: " + model.getHumidity() + " %" + "\n"
                        + "Скорость ветра: " + model.getWind() + " м/c" + "\n";
        //   + "Видимость: " + model.getVisibility() + " м" ;
    }

    public static String toUpperCaseForFirstLetter(String text) {
        StringBuilder builder = new StringBuilder(text);
        builder.setCharAt(0, Character.toUpperCase(text.charAt(0)));

        for (int i = 1; i < text.length(); i++)
            if (Character.isAlphabetic(text.charAt(i)) && Character.isSpaceChar(text.charAt(i - 1))) {
                builder.setCharAt(i - 1, '-');
                builder.setCharAt(i, Character.toUpperCase(text.charAt(i)));
            }
        return builder.toString();
    }
}