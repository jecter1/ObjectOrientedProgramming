package bot.replies;

import bot.exceptions.StorageException;
import bot.replies.builders.SimpleReplyBuilder;
import bot.storage.FileListStorage;
import bot.storage.IListStorage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ClearAllReply implements IReply {
    @Override
    public SendMessage getMessage(ReplyContext context) {
        context.setReadyForClear(false);
        context.setReadyForRecord(false);

        Message message = context.getUpdate().getMessage();

        String replyText;
        IListStorage storage = new FileListStorage();
        try {
            storage.clearAll(message.getChatId());
            replyText = SUCCESS_REPLY;
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

    private static final String SMILE_FROWNING = "\uD83D\uDE26";
    private static final String ERROR_REPLY    = "Не получилось очистить список " + SMILE_FROWNING;
    private static final String SUCCESS_REPLY  = "Очистил";
}