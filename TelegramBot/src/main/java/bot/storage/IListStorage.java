package bot.storage;

import bot.exceptions.NameDoesNotExistException;
import bot.exceptions.StorageException;
import bot.exceptions.TooMuchMoneyException;
import bot.exceptions.TooManyRecordsException;

import java.util.List;

public interface IListStorage {
    void clear(Long chatID, String name)
            throws StorageException, NameDoesNotExistException;

    void clearAll(Long chatID)
            throws StorageException;

    List<DebtInfo> getDebtList(Long chatID)
            throws StorageException;

    void changeDebt(Long chatID, DebtInfo debtInfo)
            throws StorageException, TooManyRecordsException, TooMuchMoneyException;
}
