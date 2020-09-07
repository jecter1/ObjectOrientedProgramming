package bot.replies;

import bot.replies.builders.SimpleReplyBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RecordPrepareReply implements IReply {
    @Override
    public SendMessage getMessage(ReplyContext context) {
        context.setReadyForClear(false);
        context.setReadyForRecord(true);

        Message message = context.getUpdate().getMessage();

        SimpleReplyBuilder builder = new SimpleReplyBuilder();

        String triggerMenu = context.getReplyClassName().getKey(MenuReply.class.getSimpleName());
        builder.addButton(triggerMenu);
        builder.commitRow();

        builder.setReplyID(message);
        builder.setReplyText(TEXT_REPLY);

        return builder.build();
    }

    static final String DEFAULT_SEPARATOR = ":";

    private static final String TEXT_REPLY = "Введи сообщение в формате\n" +
                                             "<ИМЯ>" + DEFAULT_SEPARATOR + "<СУММА>\n\n" +
                                             "Ограничения\n" +
                                             "<ИМЯ> - русские/английские буквы и пробелы, " +
                                             "максимальная длина - 50 символов\n" +
                                             "<СУММА> - ненулевое целое число, " +
                                             "суммарный долг не может превышать по модулю 1.000.000.000";
}