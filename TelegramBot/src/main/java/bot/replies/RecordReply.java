package bot.replies;

import bot.exceptions.IncorrectDebtInfoException;
import bot.exceptions.StorageException;
import bot.exceptions.TooMuchMoneyException;
import bot.exceptions.TooManyRecordsException;
import bot.replies.builders.SimpleReplyBuilder;
import bot.storage.DebtInfo;
import bot.storage.FileListStorage;
import bot.storage.IListStorage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RecordReply implements IReply {
    private final DebtInfo m_debtInfo;
    public RecordReply(String text) throws IncorrectDebtInfoException {
        m_debtInfo = new DebtInfo(text, RecordPrepareReply.DEFAULT_SEPARATOR);
    }

    @Override
    public SendMessage getMessage(ReplyContext context) {
        context.setReadyForClear(false);
        context.setReadyForRecord(false);

        Message message = context.getUpdate().getMessage();

        String replyText;
        IListStorage storage = new FileListStorage();
        try {
            storage.changeDebt(message.getChatId(), m_debtInfo);
            replyText = SUCCESS_REPLY;
        } catch (TooManyRecordsException exc) {
            replyText = MANY_RECORDS_REPLY;
        }  catch (TooMuchMoneyException exc) {
            replyText = MUCH_MONEY_REPLY;
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
    private static final String ERROR_REPLY        = "Не получилось записать " + SMILE_FROWNING;
    private static final String SUCCESS_REPLY      = "Записал";
    private static final String MANY_RECORDS_REPLY = "В списке слишком много записей";
    private static final String MUCH_MONEY_REPLY   = "Сумма стала слишком большой, не буду такое записывать";

}