import exceptions.DataExpiredException;
import exceptions.DataNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDataStore implements DataStore{
    private final Map<String, String> data;
    private final Map<String, Long> dataWithTTL;

    public InMemoryDataStore() {
        this.dataWithTTL = new HashMap<>();
        this.data = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        data.put(key, value);
    }

    @Override
    public void put(String key, String value, int TTL) {
        data.put(key,value);
        dataWithTTL.put(key, (System.currentTimeMillis() + TTL));
    }

    public Boolean isExpired(String key) {
        Long TTL = dataWithTTL.get(key);
        if(TTL < System.currentTimeMillis())
        {
            data.remove(key);
            dataWithTTL.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public String get(String key) throws DataExpiredException, DataNotFoundException {
        if(!data.containsKey(key)) {
            throw new DataNotFoundException("There is no such a data!");
        }

        if(dataWithTTL.containsKey(key)) {
            if(isExpired(key)) {
                throw new DataExpiredException("Data was expired!");
            }
        }
        return data.get(key);
    }
}
