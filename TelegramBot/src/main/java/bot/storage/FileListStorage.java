package bot.storage;

import bot.exceptions.NameDoesNotExistException;
import bot.exceptions.StorageException;
import bot.exceptions.TooMuchMoneyException;
import bot.exceptions.TooManyRecordsException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileListStorage implements IListStorage {
    @Override
    public void clear(Long chatID, String name) throws StorageException, NameDoesNotExistException {
        String debtFilename = DIRECTORY_STORAGE + chatID.toString() + FILENAME_EXTENSION;
        File   debtFile     = new File(debtFilename);

        // debtFile does not exist |-> nothing to clear
        if (!debtFile.exists()) {
            return;
        }

        // debtFile exists |-> copy old file to tmp file with changes
        boolean nameExists = false;
        boolean fileContainsInfo = false;
        String tmpFilename = DIRECTORY_STORAGE + FILENAME_TMP + chatID.toString() + FILENAME_EXTENSION;
        try (BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream(debtFile)));
             FileWriter     fWriter = new FileWriter(tmpFilename)) {
            String line;
            while ((line = fReader.readLine()) != null) {
                DebtInfo curDebtInfo = new DebtInfo(line, FILE_SEPARATOR);
                if (!curDebtInfo.getName().equals(name)) {
                    fileContainsInfo = true;
                    fWriter.write(curDebtInfo.asString(FILE_SEPARATOR));
                } else {
                    nameExists = true;
                }
            }
        } catch (Exception exc) {
            throw new StorageException();
        }

        // if updated file contains info |-> delete old file and rename tmp filename to old filename
        // else |-> delete both files
        try {
            debtFile.delete();
            File tmpFile = new File(tmpFilename);
            if (fileContainsInfo) {
                tmpFile.renameTo(debtFile);
            } else {
                tmpFile.delete();
            }
        } catch (Exception exc) {
            throw new StorageException();
        }

        if (!nameExists) {
            throw new NameDoesNotExistException();
        }
    }

    @Override
    public void clearAll(Long chatID) throws StorageException {
        String debtFilename = DIRECTORY_STORAGE + chatID.toString() + FILENAME_EXTENSION;
        File   debtFile     = new File(debtFilename);
        if (!debtFile.delete()) {
            throw new StorageException();
        }
    }

    @Override
    public List<DebtInfo> getDebtList(Long chatID) throws StorageException {
        List<DebtInfo> debtList     = new ArrayList<>();
        String         debtFilename = DIRECTORY_STORAGE + chatID.toString() + FILENAME_EXTENSION;
        File           debtFile     = new File(debtFilename);

        // debtFile exists |-> read file and add debtInfo to debtList
        if (debtFile.exists()) {
            try (BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream(debtFile)))) {
                String line;
                while ((line = fReader.readLine()) != null) {
                    DebtInfo debtInfo = new DebtInfo(line, FILE_SEPARATOR);
                    debtList.add(debtInfo);
                }
            } catch (Exception exc) {
                throw new StorageException();
            }
        }

        return debtList;
    }

    @Override
    public void changeDebt(Long chatID, DebtInfo debtInfo)
            throws StorageException, TooManyRecordsException, TooMuchMoneyException {
        if (debtInfo.getMoney() == EMPTY_DEBT) {
            return;
        }

        String debtFilename = DIRECTORY_STORAGE + chatID.toString() + FILENAME_EXTENSION;
        File   debtFile     = new File(debtFilename);

        // debtFile does not exist |-> make a new debtFile
        if (!debtFile.exists()) {
            if (debtInfo.getMoney() >= MAX_MONEY || debtInfo.getMoney() <= MIN_MONEY) {
                throw new TooMuchMoneyException();
            }

            try {
                debtFile.createNewFile();
            } catch (Exception exc) {
                throw new StorageException();
            }

            try (FileWriter fileWriter = new FileWriter(debtFile)) {
                fileWriter.write(debtInfo.asString(FILE_SEPARATOR));
            } catch (IOException exc) {
                throw new StorageException();
            }
            return;
        }

        // debtFile exists |-> copy old file to tmp file with changes
        int recordsCnt = RECORDS_CNT_START;
        boolean isTooMuchMoney = false;
        String tmpFilename = DIRECTORY_STORAGE + FILENAME_TMP + chatID.toString() + FILENAME_EXTENSION;
        try (BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream(debtFile)));
             FileWriter     fWriter = new FileWriter(tmpFilename)) {
            String line;
            boolean debtInfoExists = false;
            while ((line = fReader.readLine()) != null) {
                DebtInfo curDebtInfo = new DebtInfo(line, FILE_SEPARATOR);
                ++recordsCnt;
                if (curDebtInfo.getName().equals(debtInfo.getName())) {
                    debtInfoExists = true;

                    if (curDebtInfo.getMoney() + debtInfo.getMoney() >= MAX_MONEY ||
                        curDebtInfo.getMoney() + debtInfo.getMoney() <= MIN_MONEY) {
                        isTooMuchMoney = true;
                    } else {
                        curDebtInfo.addMoney(debtInfo.getMoney());
                        if (curDebtInfo.getMoney() != EMPTY_DEBT) {
                            fWriter.write(curDebtInfo.asString(FILE_SEPARATOR));
                        } else {
                            --recordsCnt;
                        }
                    }
                } else {
                    fWriter.write(curDebtInfo.asString(FILE_SEPARATOR));
                }
            }

            // create a new record if it did not exist
            if (!debtInfoExists && recordsCnt < RECORDS_CNT_MAX) {
                fWriter.write(debtInfo.asString(FILE_SEPARATOR));
            }
        } catch (Exception exc) {
            throw new StorageException();
        }

        if (recordsCnt >= RECORDS_CNT_MAX) {
            try {
                File tmpFile = new File(tmpFilename);
                tmpFile.delete();
            } catch (Exception exc) {
                throw new StorageException();
            }

            throw new TooManyRecordsException();
        }

        if (isTooMuchMoney) {
            try {
                File tmpFile = new File(tmpFilename);
                tmpFile.delete();
            } catch (Exception exc) {
                throw new StorageException();
            }

            throw new TooMuchMoneyException();
        }

        // if updated file contains info |-> delete old file and rename tmp filename to old filename
        // else |-> delete both files
        try {
            debtFile.delete();
            File tmpFile = new File(tmpFilename);
            if (recordsCnt != RECORDS_CNT_START) {
                tmpFile.renameTo(debtFile);
            } else {
                tmpFile.delete();
            }
        } catch (Exception exc) {
            throw new StorageException();
        }
    }

    private static final int    RECORDS_CNT_START  = 0;
    private static final int    RECORDS_CNT_MAX    = 100;

    private static final int    MAX_MONEY          = 1000000000;
    private static final int    MIN_MONEY          = -1000000000;

    private static final int    EMPTY_DEBT         = 0;
    private static final String DIRECTORY_STORAGE  = "debtStorage/";
    private static final String FILE_SEPARATOR     = ";";
    private static final String FILENAME_EXTENSION = ".debtlist";
    private static final String FILENAME_TMP       = "tmp";
}
