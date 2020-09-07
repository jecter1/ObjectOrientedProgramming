package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Application {
    public static void main(String  [] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        Properties properties = new Properties();
        try (InputStream fin = Application.class.getClassLoader().getResourceAsStream(FILENAME_PROPERTIES)) {
            properties.load(fin);
        } catch (IOException exc) {
            System.err.println(EXC_PREFIX_PROPERTIES + exc);
        }

        TelegramBot telegramBot = new TelegramBot(properties);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (Exception exc) {
            System.err.println(EXC_PREFIX_REGISTER + exc);
        }
    }

    private static final String FILENAME_PROPERTIES   = "DebtBot.properties";
    private static final String EXC_PREFIX_PROPERTIES = "Unable to read bot properties: ";
    private static final String EXC_PREFIX_REGISTER   = "Unable to register bot: ";
}
