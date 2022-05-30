package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Medicine {
    public String name;
    int price;
    LocalDate create;
    LocalDate expiration;

    public Medicine(String name, int price, LocalDate create, LocalDate expiration) {
        this.name = name;
        this.price = price;
        this.create = create;
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "models.Medicine info : \n"+
                "  " +    "  -name='" + name + '\'' +"\n" +
                "  " +    "  -price=" + price +"\n" +
                "  " +    "  -create=" + create.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) +"\n" +
                "  " +    "  -expiration=" + expiration.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
