package bot.replies.builders;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

// This class is used to avoid code duplication
public class SimpleReplyBuilder {
    private final SendMessage         m_sendMessage;
    private boolean                   m_isMarkdownEnabled;
    private Long                      m_chatID;
    private Integer                   m_messageID;
    private String                    m_replyText;

    private final ReplyKeyboardMarkup m_replyKeyboardMarkup;
    private final List<KeyboardRow>   m_keyboardRowList;
    private KeyboardRow               m_currentRow;
    private boolean                   m_isSelective;
    private boolean                   m_isResizable;
    private boolean                   m_isOneTime;

    public SimpleReplyBuilder() {
        m_sendMessage         = new SendMessage();
        m_isMarkdownEnabled   = DEFAULT_IS_MARKDOWN_EN;
        m_chatID              = null;
        m_messageID           = null;
        m_replyText           = null;

        m_currentRow          = new KeyboardRow();
        m_keyboardRowList     = new ArrayList<>();
        m_replyKeyboardMarkup = new ReplyKeyboardMarkup();
        m_isSelective         = DEFAULT_IS_SELECTIVE;
        m_isResizable         = DEFAULT_IS_RESIZABLE;
        m_isOneTime           = DEFAULT_IS_ONE_TIME;
    }

    // Optional
    public void enableMarkdown(boolean isMarkdownEnabled) {
        m_isMarkdownEnabled = isMarkdownEnabled;
    }

    public void setReplyID(Message message) {
        m_chatID = message.getChatId();
        m_messageID = message.getMessageId();
    }

    public void setReplyText(String replyText) {
        m_replyText = replyText;
    }

    public void addButton(String text) {
        m_currentRow.add(text);
    }

    public void commitRow() {
        if (m_currentRow.isEmpty()) return;
        m_keyboardRowList.add(m_currentRow);
        m_currentRow = new KeyboardRow();
    }

    // Optional
    public void setSelective(boolean isSelective) {
        m_isSelective = isSelective;
    }

    // Optional
    public void setResizable(boolean isResizable) {
        m_isResizable = isResizable;
    }

    // Optional
    public void setOneTime(boolean isOneTime) {
        m_isOneTime = isOneTime;
    }

    public SendMessage build() {
        if (m_chatID == null || m_messageID == null || m_replyText == null) return null;
        if (!m_keyboardRowList.isEmpty()) {
            m_replyKeyboardMarkup.setSelective(m_isSelective);
            m_replyKeyboardMarkup.setResizeKeyboard(m_isResizable);
            m_replyKeyboardMarkup.setOneTimeKeyboard(m_isOneTime);
            m_replyKeyboardMarkup.setKeyboard(m_keyboardRowList);

            m_sendMessage.setReplyMarkup(m_replyKeyboardMarkup);
        }
        m_sendMessage.enableMarkdown(m_isMarkdownEnabled);
        m_sendMessage.setChatId(m_chatID);
        m_sendMessage.setReplyToMessageId(m_messageID);
        m_sendMessage.setText(m_replyText);

        return m_sendMessage;
    }

    private static final boolean DEFAULT_IS_MARKDOWN_EN = true;

    private static final boolean DEFAULT_IS_SELECTIVE   = false;
    private static final boolean DEFAULT_IS_RESIZABLE   = true;
    private static final boolean DEFAULT_IS_ONE_TIME    = false;
}
