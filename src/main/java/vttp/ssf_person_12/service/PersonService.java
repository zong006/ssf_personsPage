package vttp.ssf_person_12.service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf_person_12.model.Person;
import vttp.ssf_person_12.repo.PersonRepo;
import vttp.ssf_person_12.utility.Util;

@Service
public class PersonService implements Util{

    @Autowired
    PersonRepo personRepo;
    
    public List<Person> getPersons() throws ParseException{

        List<Person> personList = new ArrayList<>();
        Set<String> keys = personRepo.getKeys();
        
        for (String key : keys){
            String entry = personRepo.getPersonById(key);
            Person p = generatePersonFromRedis(key, entry);
            personList.add(p);
        }
        return personList;
    }

    public Person getPersonById(String id) throws IOException, ParseException{
        String entry = personRepo.getPersonById(id);
        return generatePersonFromRedis(id, entry);
    }

    public String deletePerson(Person p) throws IOException, ParseException{
        return (personRepo.deleteById(p.getId()))? "deleted" : "not successful";
    }

    public void addPerson(Person p) throws IOException, ParseException, IllegalArgumentException, IllegalAccessException{
        String entry = parsePerson(p);
        personRepo.create(p.getId(), entry);
    }

    public void updatePerson(Person p) throws IOException, ParseException, IllegalArgumentException, IllegalAccessException{
        personRepo.deleteById(p.getId());
        String entry = parsePerson(p);
        personRepo.create(p.getId(), entry);
    }


    public String parsePerson(Person p) throws IllegalArgumentException, IllegalAccessException{
        String toSave = p.getFirstName()+","
                        +p.getLastName()+","
                        +p.getDateOfBirth().toString()+","
                        +p.getEmail()+","
                        +p.getPhoneNumber();
        
        return toSave;
    }

    public Person generatePersonFromRedis(String id, String entry) throws ParseException{
        
        String[] fields = entry.split(",");
        Person p = new Person();

        p.setId(id);
        p.setFirstName(fields[0]);
        p.setLastName(fields[1]);
        
        String dob = fields[2]; //Tue Oct 29 00:00:00 GMT+08:00 2024
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        Date dDob = sdf.parse(dob);

        // ----------------------- for saving as long -----------------------
        long epochTime = dDob.getTime(); //use this format to save date data
        Date dateFromEpoch = new Date(epochTime);
        // ----------------------- for saving as long -----------------------

        p.setDateOfBirth(dDob);

        p.setEmail(fields[3]);
        p.setPhoneNumber(fields[4]);

        return p;
    }
}
