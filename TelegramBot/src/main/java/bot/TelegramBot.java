package bot;

import bot.exceptions.IncorrectDebtInfoException;
import bot.exceptions.IncorrectNameException;
import bot.replies.*;

import bot.structures.IBiMap;
import bot.structures.HashBiMap;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class TelegramBot extends TelegramLongPollingBot {
    private final IBiMap<String, String> m_replyClassName;
    private final String m_name;
    private final String m_token;
    private final ReplyContext m_context;

    TelegramBot(Properties properties) {
        m_name  = properties.getProperty(PROPERTY_NAME_STRING);
        m_token = properties.getProperty(PROPERTY_TOKEN_STRING);

        m_replyClassName = new HashBiMap<>();
        try (Scanner configFile = new Scanner(getClass().getResourceAsStream(CONFIG_FILENAME))) {
            while (configFile.hasNextLine()) {
                String[] replyInfo = configFile.nextLine().split(CONFIG_SEPARATOR);
                m_replyClassName.put(replyInfo[USABLE_NAME_INDEX], replyInfo[CLASS_NAME_INDEX]);
            }
        }
        m_context = new ReplyContext(m_replyClassName);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) return;
        Message message = update.getMessage();
        if (!message.hasText()) return;

        IReply reply;
        try {
            String replyTriggerText = message.getText();
            String replyClassName   = m_replyClassName.getVal(replyTriggerText);

            reply = (IReply) Class.
                        forName(REPLY_PACKAGE_PREFIX + replyClassName).
                        getConstructor().
                        newInstance();

        } catch (ReflectiveOperationException exc1) {
            try {
                if (m_context.isReadyForRecord()) {
                    reply = new RecordReply(message.getText());
                } else if (m_context.isReadyForClear()) {
                    reply = new ClearReply(message.getText());
                } else {
                    reply = new UnknownReply();
                }
            } catch (IncorrectDebtInfoException exc) {
                reply = new UnknownReply();
            }
        }

        try {
            m_context.setUpdate(update);
            SendMessage sendMessage = reply.getMessage(m_context);
            execute(sendMessage);
        } catch (TelegramApiException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return m_name;
    }

    @Override
    public String getBotToken() {
        return m_token;
    }

    private static final int    USABLE_NAME_INDEX     = 0;
    private static final int    CLASS_NAME_INDEX      = 1;

    private static final String PACKAGE_SEPARATOR     = ".";
    private static final String REPLY_PACKAGE_PREFIX  = IReply.class.getPackageName() + PACKAGE_SEPARATOR;

    private static final String CONFIG_FILENAME       = "Config";
    private static final String CONFIG_SEPARATOR      = ";";

    private static final String PROPERTY_NAME_STRING  = "bot.name";
    private static final String PROPERTY_TOKEN_STRING = "bot.token";
}
