package bot.replies;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FunctionalInterface
public interface IReply {
    SendMessage getMessage(ReplyContext context);
}
