package marts.group.calendarAPI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.print.DocFlavor.SERVICE_FORMATTED;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class GoogleCalendarAPI {


     /**
   * Application name.
   */
  private  String APPLICATION_NAME;
  /**
   * Global instance of the JSON factory.
   */
  private  JsonFactory JSON_FACTORY;
  /**
   * Directory to store authorization tokens for this application.
   */
  private  String TOKENS_DIRECTORY_PATH ;

  /**
   * Global instance of the scopes required by this quickstart.
   * If modifying these scopes, delete your previously saved tokens/ folder.
   */
    private  List<String> SCOPES;

    private  String SERVICE_ACCOUNT_CONFIG ;

    private Calendar calendarService;




    public void configTheGoogleCalendarAPI(Properties properties){

        try{
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();


            // Устанавливем значения переменных используя свойства 
            this.APPLICATION_NAME = properties.getProperty("app.name");
            this.TOKENS_DIRECTORY_PATH = properties.getProperty("app.token.directory.path");
            this.SERVICE_ACCOUNT_CONFIG = properties.getProperty("app.service.account.config.path");
            this.JSON_FACTORY = GsonFactory.getDefaultInstance();

            // Устанавливаем приложению права для календаря 
            this.SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
            





            GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(SERVICE_ACCOUNT_CONFIG))
                .createScoped(SCOPES);


            // Создаем API для клиента 
            this.calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();


                System.out.println("Client Endpoint API successful created!");


        }catch(Exception e){
            e.printStackTrace();

        }


        
    }



    // 1 - Имя клиента , 2 - дата записи , 3 - имя матера , 4 - лист услуг 
    public void createAEvent(){

        try{

        // пример cоздание нового события
        Event event = new Event()
                .setSummary("Новая стрижка")
                .setDescription("Стрижка для клиента");

        // Задание времени начала и окончания события
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 3600000); // +1 hour
        DateTime startDateTime = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        EventDateTime start = new EventDateTime().setDateTime(startDateTime);
        event.setStart(start);
        DateTime endDateTime = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        EventDateTime end = new EventDateTime().setDateTime(endDateTime);
        event.setEnd(end);

        // Добавление события в календарь
         String calendarId = ""; // Здесь указать уникальный адрес календаря в профиле барбера 
         event = this.calendarService.events().insert(calendarId, event).execute();



        }catch(Exception e){
            e.printStackTrace();

        }
        
    }








  
    
}
