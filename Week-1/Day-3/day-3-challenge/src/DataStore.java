import exceptions.DataExpiredException;
import exceptions.DataNotFoundException;
import exceptions.FileNotSavedException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DataStore {
    void put(String key, String value) throws IOException, FileNotSavedException;
    void put(String key, String value, int TTL) throws DataExpiredException;
    String get(String key) throws DataExpiredException, DataNotFoundException;
}
