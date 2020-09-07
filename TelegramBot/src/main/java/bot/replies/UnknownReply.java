package bot.replies;

import bot.replies.builders.SimpleReplyBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UnknownReply implements IReply {
    @Override
    public SendMessage getMessage(ReplyContext context) {
        Message message = context.getUpdate().getMessage();

        SimpleReplyBuilder builder = new SimpleReplyBuilder();

        builder.setReplyID(message);
        builder.setReplyText(TEXT_REPLY);

        return builder.build();
    }

    private static final String TEXT_REPLY = "Просто нажимай на кнопки и следуй инструкциям";
}