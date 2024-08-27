import exceptions.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileDataStore implements DataStore{
    private final String fileName;
    private Map<String, String> datas;
    private Map<String, Long> datasWithTTL;

    public FileDataStore(String fileName) throws IOException {
        this.fileName = fileName;
        this.datas = new HashMap<>();
        this.datasWithTTL = new HashMap<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            Map<String, String> readData = (HashMap<String, String>) ois.readObject();
            if(readData != null) {
                datas = readData;
            }
        } catch (FileNotFoundException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void put(String key, String value) throws FileNotSavedException {
        datas.put(key, value);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this.datas);
            oos.flush();
        }catch (IOException e) {
            throw new FileNotSavedException("File not saved!");
        }
    }

    @Override
    public void put(String key, String value, int TTL) {
        datas.put(key,value);
        datasWithTTL.put(key, System.currentTimeMillis() + TTL);
    }

    @Override
    public String get(String key) throws DataExpiredException, DataNotFoundException {
        if(!datas.containsKey(key)) {
            throw new DataNotFoundException("There is no such a data!");
        }

        if(datasWithTTL.containsKey(key)) {
            if(isExpired(key)) {
                throw new DataExpiredException("Data was expired!");
            }
        }
        return datas.get(key);
    }

    public Boolean isExpired(String key) {
        Long TTL = datasWithTTL.get(key);
        if(TTL < System.currentTimeMillis())
        {
            datas.remove(key);
            datasWithTTL.remove(key);
            return true;
        }
        return false;
    }
}