package helpers;

import component.Buyer;
import component.User;
import org.instancio.Instancio;

import java.io.IOException;

public class TestHelper {

    public double getPrice(String itemPriceString){

        String cleanPriceString = itemPriceString.replace("$", "").trim();
        return Double.parseDouble(cleanPriceString);

    }

    public Buyer getBuyer() {
        return Instancio.of(Buyer.class).create();
    }

    public User getStandartUser() throws IOException {

        String username = PropertiesHelper.getProperty("standard_username");
        String password = PropertiesHelper.getProperty("standard_password");

        return new User(username, password);
    }

    public User getLockedUser() throws IOException {

        String username = PropertiesHelper.getProperty("locked_out_username");
        String password = PropertiesHelper.getProperty("locked_out_password");

        return new User(username, password);
    }

    public User getGlitchedUser() throws IOException {

        String username = PropertiesHelper.getProperty("glitch_username");
        String password = PropertiesHelper.getProperty("glitch_password");

        return new User(username, password);
    }

}
