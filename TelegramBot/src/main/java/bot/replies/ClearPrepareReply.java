package bot.replies;

import bot.replies.builders.SimpleReplyBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ClearPrepareReply implements IReply {
    @Override
    public SendMessage getMessage(ReplyContext context) {
        context.setReadyForClear(true);
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

    private static final String TEXT_REPLY = "Введи сообщение в формате\n" +
                                             "<ИМЯ>\n\n" +
                                             "Ограничения\n" +
                                             "<ИМЯ> - одно из имен в твоем списке, " +
                                             "русские/английские буквы и пробелы, " +
                                             "максимальная длина - 50 символов";
}