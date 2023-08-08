package marts.group.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import marts.group.calendarAPI.GoogleCalendarAPI;
import marts.group.pojo.Order;

/**
 * Hello world!
 *
 */
public class MainClass 
{
    



    public static void main( String[] args ){
        BotClass botClass = new BotClass();
        
        botClass
            .configSystem()
            .startBot();
    }


    
}

class BotClass extends TelegramLongPollingBot{
    private Properties telegramBotProperties = new Properties();
    private GoogleCalendarAPI googleCalendarAPI;
    private String botName;
    private String botToken;
    private Map<Long , Order> personOrder = new HashMap<>();



    public BotClass configSystem(){
        try{

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("application.properties");
            this.telegramBotProperties.load(inputStream);
            inputStream.close();

            this.botName = this.telegramBotProperties.getProperty("tg.bot.name");
            this.botToken = this.telegramBotProperties.getProperty("tg.bot.token");
            
            // Конфигурируем календарь 
            this.googleCalendarAPI = new GoogleCalendarAPI();
            this.googleCalendarAPI.configTheGoogleCalendarAPI(telegramBotProperties);
        

        }catch(Exception e){

            e.printStackTrace();
        }

        return this;

    }

    public void startBot(){

        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);

        }catch(Exception e){

            e.printStackTrace();

        }

    }





    @Override
    public void onUpdateReceived(Update update) {
        String msg = "";
        Long chatId = 0L;
        Integer messageId ;


        if(update.hasCallbackQuery()){

            msg = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
            messageId = update.getCallbackQuery().getMessage().getMessageId();

            try{
                switch(msg){
                    case "BACK" : 
                        welcomeMessageWithServices(chatId);

                         execute(
                            EditMessageReplyMarkup
                            .builder()
                            .chatId(chatId)
                            .messageId(messageId)
                            .replyMarkup(null)
                            .build()

                         );
                    break;    

                }

            }catch(Exception e){
                e.printStackTrace();

            }



            return;




        }

        msg = update.getMessage().getText();
        chatId = update.getMessage().getChatId();

     


        try{

            switch(msg){

                case "/start" : 

                    welcomeMessageWithServices(chatId);
                    this.personOrder.putIfAbsent(chatId, new Order());
                break;

                case "Мужская стрижка" : 
                    mensHaircut(chatId);
                break;
                
                case "Моделирование бороды" : 
                    beardModeling(chatId);
                break;
                
                case "Королевское бритье"  :
                    royalShave(chatId);
                break;
                
                case "Окантовка" : 
                    edging(chatId);
                break;
                
                case "Детская стрижка" : 
                    childrensHaircut(chatId);
                break;
                
                case "Комплекс" : 
                    complex(chatId);
                break;
                
                case "Тонировка бороды" : 
                    beardToning(chatId);
                break;
                
                case "Комуфляж бороды" : 
                    camoBeard(chatId);
                break;

                case "Назад \u2B06\uFE0F" : 
                    welcomeMessageWithServices(chatId);
                break;    
                
    
                default : 
                    execute(
                        SendMessage
                        .builder()
                        .chatId(chatId)
                        .text("Sorry , I don't know this command , write a /help ")
                        .build()
                    );

            }

        }catch(Exception e){
            e.printStackTrace();

        }


        
        
    }


    public void welcomeMessageWithServices(Long chatId){

        KeyboardRow rowOne = new KeyboardRow(List.of(
            new KeyboardButton("Мужская стрижка") ,
            new KeyboardButton("Моделирование бороды ")  
        ));

        KeyboardRow rowTwo = new KeyboardRow(List.of(
            new KeyboardButton("Королевское бритье") ,
            new KeyboardButton("Окантовка")  
        ));

        KeyboardRow rowThree = new KeyboardRow(List.of(
            new KeyboardButton("Детская стрижка") ,
            new KeyboardButton("Комплекс")  
        ));

        KeyboardRow rowFor = new KeyboardRow(List.of(
            new KeyboardButton("Тонировка бороды ") ,
            new KeyboardButton("Комуфляж бороды ")  
        ));

        ReplyKeyboardMarkup  replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(rowOne, rowTwo, rowThree, rowFor));
        replyKeyboardMarkup.setResizeKeyboard(true);

        try{
            execute(

                SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Hello")
                    .replyMarkup(replyKeyboardMarkup)
                    .build()
            );

        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public void mensHaircut(Long chatId){

        try{

            KeyboardRow row = new KeyboardRow(List.of(new KeyboardButton("Назад \u2B06\uFE0F")));
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(Collections.singletonList(row));
            replyKeyboardMarkup.setResizeKeyboard(true);

            
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(
                
                Collections.singletonList(
                  List.of(
                    InlineKeyboardButton
                        .builder()
                        .callbackData("MENS_HAIRCUIT")
                        .text("Записаться \u270D\uFE0F")
                        .build() , 

                        InlineKeyboardButton
                        .builder()
                        .callbackData("BACK")
                        .text("Назад \u2B06\uFE0F")
                        .build()
                  )  
                )
            );



            execute(

                SendMessage
                .builder()
                .chatId(chatId)
                .text(
                    "\u0421\u043E\u0432\u0440\u0435\u043C\u0435\u043D\u043D\u044B\u0435 \u043C\u0443\u0436\u0447\u0438\u043D\u044B \u0443\u0434\u0435\u043B\u044F\u044E\u0442 \u0431\u043E\u043B\u044C\u0448\u043E\u0435 \u0437\u043D\u0430\u0447\u0435\u043D\u0438\u044F \u0441\u0432\u043E\u0435\u043C\u0443 \u0432\u043D\u0435\u0448\u043D\u0435\u043C\u0443 \u0432\u0438\u0434\u0443 \u0432 \u0446\u0435\u043B\u043E\u043C \u0438 \u043F\u0440\u0438\u0447\u0435\u0441\u043A\u0435 \u0432 \u0447\u0430\u0441\u0442\u043D\u043E\u0441\u0442\u0438.\n" + //
                            "\u0412\u0435\u0434\u044C \u0431\u043B\u0430\u0433\u043E\u0434\u0430\u0440\u044F \u0441\u0442\u0440\u0438\u0436\u043A\u0435 \u043C\u043E\u0436\u043D\u043E \u0434\u043E \u043D\u0435\u0443\u0437\u043D\u0430\u0432\u0430\u0435\u043C\u043E\u0441\u0442\u0438 \u0438\u0437\u043C\u0435\u043D\u0438\u0442\u044C \u0432\u043D\u0435\u0448\u043D\u043E\u0441\u0442\u044C.\n" + //
                            "\u041D\u0430\u0438\u0431\u043E\u043B\u0435\u0435 \u0430\u043A\u0442\u0443\u0430\u043B\u044C\u043D\u044B\u043C \u0442\u0440\u0435\u043D\u0434\u043E\u043C \u043D\u0435\u0441\u043A\u043E\u043B\u044C\u043A\u0438\u0445 \u043F\u043E\u0441\u043B\u0435\u0434\u043D\u0438\u0445 \u043B\u0435\u0442 \u044F\u0432\u043B\u044F\u0435\u0442\u0441\u044F \u0430\u043A\u043A\u0443\u0440\u0430\u0442\u043D\u043E\u0441\u0442\u044C \u0434\u0430 \u0438 \u0443\u0445\u043E\u0436\u0435\u043D\u043D\u043E\u0441\u0442\u044C.\n" + //
                            "\u0410 \u0432\u043E\u0442 \u0432\u044B\u0431\u043E\u0440 \u043C\u0443\u0436\u0441\u043A\u043E\u0439 \u0441\u0442\u0440\u0438\u0436\u043A\u0438 \u0437\u0430\u0432\u0438\u0441\u0438\u0442 \u043E\u0442 \u043A\u0430\u0436\u0434\u043E\u0433\u043E \u043A\u043E\u043D\u043A\u0440\u0435\u0442\u043D\u043E\u0433\u043E \u0441\u043B\u0443\u0447\u0430\u044F.\n" + //
                            "\n" + //
                            "\u041F\u0440\u0438\u0447\u0435\u0441\u043A\u0430, \u0431\u0435\u0441\u0441\u043F\u043E\u0440\u043D\u043E, \u0434\u043E\u043B\u0436\u043D\u0430 \u043D\u0435 \u0442\u043E\u043B\u044C\u043A\u043E \u043E\u0442\u0432\u0435\u0447\u0430\u0442\u044C \u0442\u0432\u043E\u0435\u043C\u0443 \u043E\u0431\u0440\u0430\u0437\u0443 \u0436\u0438\u0437\u043D\u0438, \u043D\u043E \u0438 \u043E\u0431\u044F\u0437\u0430\u0442\u0435\u043B\u044C\u043D\u043E \u043F\u043E\u0434\u0447\u0435\u0440\u043A\u0438\u0432\u0430\u0442\u044C \u043F\u0440\u0435\u0438\u043C\u0443\u0449\u0435\u0441\u0442\u0432\u0430 \u0443\u043D\u0438\u043A\u0430\u043B\u044C\u043D\u044B\u0445 \u0444\u043E\u0440\u043C \u0442\u0432\u043E\u0435\u0433\u043E \u043B\u0438\u0446\u0430.\n" + //
                            "\n" + //
                            "\u0412 \u0441\u0442\u0440\u0438\u0436\u043A\u0443 \u0432\u0445\u043E\u0434\u0438\u0442:\n" + //
                            " \u2022 \u043C\u044B\u0442\u044C\u0435 \u0433\u043E\u043B\u043E\u0432\u044B \u0434\u043E \u0441\u0442\u0440\u0438\u0436\u043A\u0438;\n" + //
                            " \u2022 \u0441\u0442\u0440\u0438\u0436\u043A\u0430 \u043C\u0430\u0448\u0438\u043D\u043A\u043E\u0439 \u0438 \u043D\u043E\u0436\u043D\u0438\u0446\u0430\u043C\u0438;\n" + //
                            " \u2022 \u043C\u044B\u0442\u044C\u0435 \u0433\u043E\u043B\u043E\u0432\u044B \u043F\u043E\u0441\u043B\u0435 \u0441\u0442\u0440\u0438\u0436\u043A\u0438;\n" + //
                            " \u2022 \u0443\u043A\u043B\u0430\u0434\u043A\u0430 \u0441 \u0438\u0441\u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u043D\u0438\u0435\u043C \u0441\u0442\u0430\u0439\u043B\u0438\u043D\u0433\u0430;\n" + //
                            " \u2022 \u043F\u0440\u043E\u0444\u0435\u0441\u0441\u0438\u043E\u043D\u0430\u043B\u044C\u043D\u0430\u044F \u043C\u0443\u0436\u0441\u043A\u0430\u044F \u043A\u043E\u0441\u043C\u0435\u0442\u0438\u043A\u0430 \u0438\u0437\u0432\u0435\u0441\u0442\u043D\u044B\u0445 \u0431\u0440\u0435\u043D\u0434\u043E\u0432.\n" + //
                            "\n" + //
                            "\u0426\u0435\u043D\u0430 \u0443\u0441\u043B\u0443\u0433\u0438 450"
                    )
                .replyMarkup(inlineKeyboardMarkup)
                .build()
            );



        }catch(Exception e){
            e.printStackTrace();

        }
        
    }

    public void beardModeling(Long chatId){

    }

    public void royalShave(Long chatId){


    }

    public void edging(Long chatId){

    } 

    public void childrensHaircut(Long chatId){


    }

    public void complex(Long chatId){

    }

    public void beardToning(Long chatId){


    }

    public void camoBeard(Long chatId){

    }


    @Override
    public String getBotToken() {
        return botToken;
    }


    @Override
    public String getBotUsername() {
        return botName;
    }





}
