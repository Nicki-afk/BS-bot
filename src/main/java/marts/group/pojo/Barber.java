package marts.group.pojo;

public class Barber {

    private String firstName;
    private String lastName;
    private String calendarMailAdress;




    public Barber(){}

    


    // calendaarMailAdress будет добовлятся через property файл по ключю. Ключем будет являтся имя барбера 
    // Пример : barber.anton.calendar.mail.adress=wkfnwofweoifjwiofj@wfwefw.com
    public Barber(String firstName, String lastName, String calendarMailAdress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.calendarMailAdress = calendarMailAdress;
    }



    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getCalendarMailAdress() {
        return calendarMailAdress;
    }
    public void setCalendarMailAdress(String calendarMailAdress) {
        this.calendarMailAdress = calendarMailAdress;
    }


    
    
}
