package ku.cs.shop.services;

import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.services.DataSource;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountListFileDataSource implements DataSource<AccountList>{
    private String directoryName;
    private String filename;

    public AccountListFileDataSource(){
        this("csv-data","accounts.csv");

    }

    /**
     * Testing purpose
     * @param directoryName directory name of testing
     * @param filename file name for testing
     */
    // constructor
    public AccountListFileDataSource(String directoryName, String filename) {
        this.directoryName = directoryName;
        this.filename = filename;
        initialFileIfNotExist();
    }

    private void initialFileIfNotExist(){
        File file = new File(directoryName);
        if (!file.exists()){
            file.mkdir(); // make directory
        }

        String path = directoryName + File.separator + filename;
        file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public AccountList readData(){
        AccountList accountList = new AccountList();

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                Account account = new Account(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim(),
                        data[6].trim(),
                        data[7].trim(),
                        LocalDateTime.parse(data[8].trim(), DateTimeFormatter.ofPattern("HH:mm-dd-MM-yyyy"))
                );
                accountList.addAccount(account);
                accountList.sort();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return accountList;
    }

    @Override
    public void writeData(AccountList accountList) {
        String path = directoryName + File.separator +filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try{
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(accountList.toCsv());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }



}