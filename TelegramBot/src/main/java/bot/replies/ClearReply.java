package bot.replies;

import bot.exceptions.IncorrectNameException;
import bot.exceptions.NameDoesNotExistException;
import bot.exceptions.StorageException;
import bot.replies.builders.SimpleReplyBuilder;
import bot.storage.DebtInfo;
import bot.storage.FileListStorage;
import bot.storage.IListStorage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ClearReply implements IReply {
    private final String m_name;

    public ClearReply(String name) throws IncorrectNameException {
        if (DebtInfo.isCorrectName(name)) {
            m_name = name;
        } else {
            throw new IncorrectNameException();
        }
    }

    @Override
    public SendMessage getMessage(ReplyContext context) {
        context.setReadyForClear(false);
        context.setReadyForRecord(false);

        Message message = context.getUpdate().getMessage();

        String replyText;
        IListStorage storage = new FileListStorage();
        try {
            storage.clear(message.getChatId(), m_name);
            replyText = SUCCESS_REPLY;
        } catch (NameDoesNotExistException exc) {
            replyText = DID_NOT_FIND_REPLY;
        } catch (StorageException exc) {
            replyText = ERROR_REPLY;
        }

        SimpleReplyBuilder builder = new SimpleReplyBuilder();

        String triggerRecordPrepare = context.getReplyClassName().getKey(RecordPrepareReply.class.getSimpleName());
        builder.addButton(triggerRecordPrepare);
        builder.commitRow();

        String triggerList = context.getReplyClassName().getKey(ListReply.class.getSimpleName());
        builder.addButton(triggerList);
        builder.commitRow();

        builder.setReplyID(message);
        builder.setReplyText(replyText);

        return builder.build();
    }

    private static final String SMILE_FROWNING     = "\uD83D\uDE26";
    private static final String ERROR_REPLY        = "Не получилось удалить запись " + SMILE_FROWNING;
    private static final String DID_NOT_FIND_REPLY = "Нет такого имени";
    private static final String SUCCESS_REPLY      = "Удалил";
}