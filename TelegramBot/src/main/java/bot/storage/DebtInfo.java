package bot.storage;

import bot.exceptions.IncorrectDebtInfoException;
import bot.exceptions.IncorrectMoneyException;
import bot.exceptions.IncorrectNameException;

public class DebtInfo {
    private final String m_name;
    private Integer      m_money;

    public DebtInfo(String str, String separator) throws IncorrectDebtInfoException {
        String[] fields = str.split(separator);
        if (fields.length != FIELDS_CNT) {
            throw new IncorrectDebtInfoException();
        }

        if (!isCorrectName(fields[INDEX_NAME])) {
            throw new IncorrectNameException();
        }

        if (!isCorrectMoney(fields[INDEX_MONEY])) {
            throw new IncorrectMoneyException();
        }

        m_name  = fields[INDEX_NAME];
        m_money = Integer.parseInt(fields[INDEX_MONEY]);
    }

    public String getName() {
        return m_name;
    }

    public Integer getMoney() {
        return m_money;
    }

    public void addMoney(Integer money) {
        m_money += money;
    }

    public String asString(String separator) {
        return getName() + separator + getMoney().toString() + NEW_LINE;
    }

    public static boolean isCorrectName(String name) {
        if (name.length() > NAME_MAX_LENGTH) {
            return false;
        }

        for (char ch : name.toCharArray()) {
            if (!isRussianLetter(ch) && !isEnglishLetter(ch) && ch != ' ') {
                return false;
            }
        }
        return true;
    }

    private static boolean isCorrectMoney(String money) {
        try {
            if (Integer.parseInt(money) == 0) return false;
        } catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }

    private static boolean isRussianLetter(char ch) {
        return ((ch >= LETTER_RU_FIRST && ch <= LETTER_RU_LAST) ||
                (ch >= LETTER_RU_FIRST + SPACING_RU && ch <= LETTER_RU_LAST + SPACING_RU));
    }

    private static boolean isEnglishLetter(char ch) {
        return ((ch >= LETTER_EN_FIRST && ch <= LETTER_EN_LAST) ||
                (ch >= LETTER_EN_FIRST + SPACING_EN && ch <= LETTER_EN_LAST + SPACING_EN));
    }

    private static final int    NAME_MAX_LENGTH = 50;

    private static final char   LETTER_RU_FIRST = 'а';
    private static final char   LETTER_RU_LAST  = 'я';
    private static final int    SPACING_RU      = 'Б' - 'б';

    private static final char   LETTER_EN_FIRST = 'a';
    private static final char   LETTER_EN_LAST  = 'z';
    private static final int    SPACING_EN      = 'B' - 'b';

    private static final int    INDEX_NAME      = 0;
    private static final int    INDEX_MONEY     = 1;
    private static final int    FIELDS_CNT      = 2;

    private static final String NEW_LINE        = "\n";
}
