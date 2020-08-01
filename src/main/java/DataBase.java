import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import java.io.IOException;
import java.util.ArrayList;

public class DataBase extends User {

    private final MongoCollection<User> collection = new MongoClient(new MongoClientURI("mongodb+srv://JeanBernad:@weatherbot-wlpyo.mongodb.net/test?retryWrites=true&w=majority"))
            .getDatabase("telegramBot")
            .withCodecRegistry(CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()
                    )))
            .getCollection("weatherBot", User.class);
    private User user;

    public DataBase(long tgId, String userName) {
        user = new User(tgId, userName, collection.count());
    }

    public String writeNewUser() {
        user.setSettledCity("-");
        user.setMarkUpToChangeCity(false);
        collection.insertOne(user);
        return "Новый пользователь!\n" + user.toString();
    }

    public boolean existanceOfUser() {

        User founded = collection.find(Filters.eq("tgId", user.getTgId())).first();

        if (founded != null) {
            return true;
        } else return false;
    }

    public String markToChangeCity(String cityName) {

        User founded = collection.find(Filters.eq("tgId", user.getTgId())).first();
        if (founded != null) {
            if (founded.isMarkUpToChangeCity()) {
                try {
                    Weather.getWeather(cityName, new Model());
                    founded.setSettledCity(Weather.toUpperCaseForFirstLetter(cityName));
                    founded.setMarkUpToChangeCity(false);
                    collection.deleteOne(Filters.eq("tgId", user.getTgId()));
                    collection.insertOne(founded);
                    return "Вы успешно установили город по умолчанию✅";

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public void deleteAndInsertForCity() {
        User founded = collection.find(Filters.eq("tgId", user.getTgId())).first();
        if (founded != null) {
            founded.setMarkUpToChangeCity(true);
            founded.setIfCitySetten(true);
            collection.deleteOne(Filters.eq("tgId", user.getTgId()));
            collection.insertOne(founded);
        }
    }

    public boolean markedToChangeCity() {
        User founded = collection.find(Filters.eq("tgId", user.getTgId())).first();

        if (founded != null) {
            return founded.isMarkUpToChangeCity();
        }
        return false;
    }

    public boolean checkOfCity() {
        User founded = collection.find(Filters.eq("tgId", user.getTgId())).first();

        if (founded != null) {
            return founded.isIfCitySetten();
        }
        return false;
    }

    public String getSettledCity() {
        User founded = collection.find(Filters.eq("tgId", user.getTgId())).first();

        if (founded != null) {
            return founded.getSettledCity();
        }
        return "";
    }

    long n;
    public ArrayList allIds() {
        n = collection.count();
        ArrayList<Long> arrayList = new ArrayList<>();
        for (long i = 0; i < n; i++) {
            User founded = collection.find(Filters.eq("number", i)).first();
            if (founded != null) {
                arrayList.add(founded.getTgId());
            }
        }
        return arrayList;
    }

    public String allUsers() {
        String s = "";
        n = collection.count();
        for (long i = 0; i < n; i++) {
            User founded = collection.find(Filters.eq("number", i)).first();
            if (founded != null)
                s += founded.toString();
        }
        return s;
    }
}