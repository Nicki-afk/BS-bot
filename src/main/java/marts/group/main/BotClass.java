package marts.group.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class BotClass 
{

    private Properties telegramBotProperties = new Properties();



    public static void main( String[] args )
    {
        
    }


    private void configBot(){
        try{

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        this.telegramBotProperties.load(inputStream);
        inputStream.close();
        

        }catch(Exception e){

            e.printStackTrace();
        }

    }
}
