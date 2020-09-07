package bot.replies;

import bot.replies.builders.SimpleReplyBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MenuReply implements IReply {
    @Override
    public SendMessage getMessage(ReplyContext context) {
        context.setReadyForClear(false);
        context.setReadyForRecord(false);

        Message message = context.getUpdate().getMessage();

        SimpleReplyBuilder builder = new SimpleReplyBuilder();

        String triggerRecordPrepare = context.getReplyClassName().getKey(RecordPrepareReply.class.getSimpleName());
        builder.addButton(triggerRecordPrepare);
        builder.commitRow();

        String triggerList = context.getReplyClassName().getKey(ListReply.class.getSimpleName());
        builder.addButton(triggerList);
        builder.commitRow();

        builder.setReplyID(message);
        builder.setReplyText(TEXT_REPLY);

        return builder.build();
    }

    private static final String TEXT_REPLY = "Вот твое меню, выбирай";
}