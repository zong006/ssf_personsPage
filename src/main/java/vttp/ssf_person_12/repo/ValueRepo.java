package vttp.ssf_person_12.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


// slide 20

@Repository
public class ValueRepo {

    @Autowired
    RedisTemplate<String, String> stringTemplate;

    public void createValue(String key, String value){
        stringTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key){
        return stringTemplate.opsForValue().get(key);
    }

    public boolean deleteValue(String key){
        // stringTemplate.opsForValue().getAndDelete(key);
        return stringTemplate.delete(key);
    }

    

}
