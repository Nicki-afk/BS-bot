package marts.group.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Hello world!
 *
 */
public class BotClass extends TelegramLongPollingBot
{

    private Properties telegramBotProperties = new Properties();
    private String botName;
    private String botToken;
    



    public static void main( String[] args )
    {
        
    }


    private void configBot(){
        try{

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        this.telegramBotProperties.load(inputStream);
        inputStream.close();

        this.botName = this.telegramBotProperties.getProperty("tg.bot.name");
        this.botToken = this.telegramBotProperties.getProperty("tg.bot.token");
        

        }catch(Exception e){

            e.printStackTrace();
        }

    }


    @Override
    public void onUpdateReceived(Update arg0) {
        
        
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
