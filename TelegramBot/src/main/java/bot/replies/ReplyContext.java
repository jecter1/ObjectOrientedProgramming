package bot.replies;

import bot.structures.IBiMap;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ReplyContext {
    private Update m_update = null;
    private final IBiMap<String, String> m_replyClassName;
    private boolean m_readyForClear;
    private boolean m_readyForRecord;

    public ReplyContext(IBiMap<String, String> replyClassName) {
        m_replyClassName = replyClassName;
        m_readyForClear  = false;
        m_readyForRecord = false;
    }

    public void setUpdate(Update update) {
        m_update = update;
    }

    public void setReadyForClear(boolean readyForClear) {
        m_readyForClear = readyForClear;
    }

    public void setReadyForRecord(boolean readyForRecord) {
        m_readyForRecord = readyForRecord;
    }

    public Update getUpdate() {
        return m_update;
    }

    public IBiMap<String, String> getReplyClassName() {
        return m_replyClassName;
    }

    public boolean isReadyForClear() {
        return m_readyForClear;
    }

    public boolean isReadyForRecord() {
        return m_readyForRecord;
    }
}
