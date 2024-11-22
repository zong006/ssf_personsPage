package vttp.ssf_person_12.repo;
    
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf_person_12.data.DataDir;

@Repository
public class PersonRepo implements DataDir{
    
    @Autowired
    RedisTemplate<String, String> stringTemplate;

    public boolean create(String id, String entry){
        stringTemplate.opsForHash().put(DataDir.redisKey, id, entry);
        return true;
    }

    public String getPersonById(String id){
        return (String) stringTemplate.opsForHash().get(DataDir.redisKey, id);
    }

    public boolean deleteById(String id){
        long number = stringTemplate.opsForHash().delete(DataDir.redisKey, id);
        return (number>0)? true : false;
    }

    public boolean updateById(String redisKey, String id, String updatedEntry){
        boolean isFound = deleteById(id);
        return isFound? create(id, updatedEntry) : false;
    }

    public Set<String> getKeys(){
        Set<Object> keys = stringTemplate.opsForHash().keys(DataDir.redisKey);
        return keys.stream().map(k -> (String) k).collect(Collectors.toSet());
    }
}
