package marts.group.pojo;

import java.util.List;

public class Order {


    private Long chatID;
    private Barber barber;
    private String time;
    private List<String>serviceList;


    public Order(){}



    

    public Order(Long chatID, Barber barber, String time, List<String> serviceList) {
        this.chatID = chatID;
        this.barber = barber;
        this.time = time;
        this.serviceList = serviceList;
    }





    public Long getChatID() {
        return chatID;
    }





    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }





    public Barber getBarber() {
        return barber;
    }





    public void setBarber(Barber barber) {
        this.barber = barber;
    }





    public String getTime() {
        return time;
    }





    public void setTime(String time) {
        this.time = time;
    }





    public List<String> getServiceList() {
        return serviceList;
    }





    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }




    



    
    
}
