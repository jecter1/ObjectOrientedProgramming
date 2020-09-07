package bot.replies;

import bot.exceptions.StorageException;
import bot.replies.builders.SimpleReplyBuilder;
import bot.storage.DebtInfo;
import bot.storage.FileListStorage;
import bot.storage.IListStorage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class ListReply implements IReply {
    @Override
    public SendMessage getMessage(ReplyContext context) {
        context.setReadyForClear(false);
        context.setReadyForRecord(false);

        Message message = context.getUpdate().getMessage();

        IListStorage  storage   = new FileListStorage();
        StringBuilder replyText = new StringBuilder();

        boolean listIsEmpty = true;
        try {
            List<DebtInfo> debtList = storage.getDebtList(message.getChatId());
            listIsEmpty = debtList.isEmpty();
            for (DebtInfo debtInfo : debtList) {
                replyText.append(debtInfo.asString(LIST_SEPARATOR));
            }
        } catch (StorageException exc) {
            replyText.append(TEXT_ERROR_LIST);
        }

        SimpleReplyBuilder builder = new SimpleReplyBuilder();

        if (listIsEmpty) {
            replyText.append(TEXT_EMPTY_LIST);

            String triggerRecordPrepare = context.getReplyClassName().getKey(RecordPrepareReply.class.getSimpleName());
            builder.addButton(triggerRecordPrepare);
            builder.commitRow();

            String triggerList = context.getReplyClassName().getKey(ListReply.class.getSimpleName());
            builder.addButton(triggerList);
        } else {
            String triggerClear = context.getReplyClassName().getKey(ClearPrepareReply.class.getSimpleName());
            builder.addButton(triggerClear);
            builder.commitRow();

            String triggerClearAll = context.getReplyClassName().getKey(ClearAllReply.class.getSimpleName());
            builder.addButton(triggerClearAll);
            builder.commitRow();

            String triggerMenu = context.getReplyClassName().getKey(MenuReply.class.getSimpleName());
            builder.addButton(triggerMenu);
        }
        builder.commitRow();

        builder.setReplyID(message);
        builder.setReplyText(replyText.toString());

        return builder.build();
    }

    private static final String SMILE_FROWNING  = "\uD83D\uDE26";
    private static final String LIST_SEPARATOR  = ": ";
    private static final String TEXT_EMPTY_LIST = "В списке ничего нет";
    private static final String TEXT_ERROR_LIST = "Не получилось прочитать список " + SMILE_FROWNING;
}