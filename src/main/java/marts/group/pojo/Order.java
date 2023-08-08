package marts.group.pojo;

import java.util.List;

public class Order {


    private Long chatID;
    private Barber barber;
    private List<String>serviceList;


    public Order(){}



    public Order(Long chatID, Barber barber, List<String> serviceList) {
        this.chatID = chatID;
        this.barber = barber;
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
    public List<String> getServiceList() {
        return serviceList;
    }
    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }



    
    
}
