import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.util.*;

public class Bot extends TelegramLongPollingBot {

    DataBase dataBase;
    String adminId = "";
    String chatId;
    String messageText;
    Model model;

    @Override
    public void onUpdateReceived(Update update) {

        model = new Model();

        messageText = update.getMessage().getText();

        String infText = "";
        if (messageText.startsWith("/pr")) {
            infText = messageText.substring(3);
            messageText = messageText.substring(0,3);
        }

        chatId = update.getMessage().getChatId().toString();

        dataBase = new DataBase(update.getMessage().getChat().getId(), update.getMessage().getChat().getUserName());

        if (!dataBase.existanceOfUser()) {

            sendMesg(adminId, dataBase.writeNewUser());
        }
        switch (messageText) {
            case "/start": {
                sendMesg(chatId, "Привет, " + update.getMessage().getChat().getFirstName() + ", я присылаю погоду.");
                sendMesg(chatId, "Просто введите название города.");
                break;
            }
            case "Установить город":
            case "Изменить город": {
                dataBase.deleteAndInsertForCity();
                sendMesg(chatId, "Введите название города.");
                break;
            }
            case "/pr": {
                if (chatId.equals(adminId)) {
                    ArrayList arrayList = dataBase.allIds();
                    long n = dataBase.n;
                    for (int i = 0; i < n; i++) {
                        sendMesg(arrayList.get(i).toString(), infText);
                    }
                }
                break;
            }
            case "/allU": {
                if (chatId.equals(adminId)) {
                    sendMesg(adminId, dataBase.allUsers());
                }
                break;
            }
            default: {
                try {
                    sendMesg(chatId, dataBase.markToChangeCity(messageText) + "\n" +  Weather.getWeather(messageText, model));
                } catch (IOException e) {
                    sendMesg(chatId, "Город не найден, попробуйте снова.");
                }
            }
        }
    }

    public void sendMesg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            setButton(sendMessage);
            execute(sendMessage);


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void setButton(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();

        if (dataBase.checkOfCity() && !dataBase.markedToChangeCity()) {
            try {
                Weather.getWeather(messageText, model);
                keyboardRow.add(new KeyboardButton(dataBase.getSettledCity()));
                keyboardRow.add(new KeyboardButton("Изменить город "));
            } catch (IOException e) {
                keyboardRow.add(new KeyboardButton("Установить город"));
            }
        }

        if (!dataBase.checkOfCity()) {
            keyboardRow.add(new KeyboardButton("Установить город"));
        }

        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public String getBotUsername() {
        return  "weatherByJeanBot";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}

