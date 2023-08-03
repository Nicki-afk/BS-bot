package marts.group.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import marts.group.calendarAPI.GoogleCalendarAPI;

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
        String msg = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();


        try{

            switch(msg){

                case "/start" : 

                    execute(
                        SendMessage
                            .builder()
                            .chatId(chatId)
                            .text("Hi i'm barbershop bot!")
                            .build()
                    ); 
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


    @Override
    public String getBotToken() {
        return botToken;
    }


    @Override
    public String getBotUsername() {
        return botName;
    }





}
