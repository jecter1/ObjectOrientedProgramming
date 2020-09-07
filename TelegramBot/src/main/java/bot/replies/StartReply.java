package bot.replies;

import bot.replies.builders.SimpleReplyBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartReply implements IReply {
    @Override
    public SendMessage getMessage(ReplyContext context) {
        context.setReadyForClear(false);
        context.setReadyForRecord(false);

        Message message = context.getUpdate().getMessage();

        SimpleReplyBuilder builder = new SimpleReplyBuilder();

        String triggerMenu = context.getReplyClassName().getKey(MenuReply.class.getSimpleName());
        builder.addButton(triggerMenu);
        builder.commitRow();

        builder.setReplyID(message);
        builder.setReplyText(TEXT_REPLY);

        return builder.build();
    }

    private static final String SMILE_WAVING_HAND     = "\uD83D\uDC4B";
    private static final String SMILE_POINT_DOWN      = "\uD83D\uDC47";
    private static final String SYMBOL_DOUBLE_NEWLINE = "\n\n";

    private static final String TEXT_REPLY =
                            "Привет! " + SMILE_WAVING_HAND +
                            SYMBOL_DOUBLE_NEWLINE +
                            "Я бот, запоминающий суммы, которые ты должен отдать или которые должны отдать тебе" +
                            SYMBOL_DOUBLE_NEWLINE +
                            "Взаимодействуй со мной, используя кнопки внизу " + SMILE_POINT_DOWN +
                            SYMBOL_DOUBLE_NEWLINE +
                            "Также иногда тебе придется вводить текст самому, но об этом я обязательно напомню";
}