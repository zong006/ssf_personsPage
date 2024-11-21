package vttp.ssf_person_12.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import vttp.ssf_person_12.data.DataDir;
import vttp.ssf_person_12.model.Person;

@Service
public class PersonService implements DataDir{
    
    public List<Person> getPersons() throws IOException, ParseException{
        List<Person> personList = new ArrayList<>();
        File filePath = new File(dataFolder, DataDir.filePath);
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);

        String lineRead = "";
        
        while ((lineRead = br.readLine())!=null){
            String[] terms = lineRead.split(",");
            Person p = new Person();
            p.setId(terms[0]);
            p.setFirstName(terms[1]);
            p.setLastName(terms[2]);

            String dob = terms[3]; //Tue Oct 29 00:00:00 GMT+08:00 2024
            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
            Date dDob = sdf.parse(dob);
            p.setDateOfBirth(dDob);
            p.setEmail(terms[4]);
            p.setPhoneNumber(terms[5]);

            personList.add(p);
        }

        br.close();
        fr.close();
        return personList;
    }

    public void deletePerson(Person p) throws IOException, ParseException{
        List<Person> personList = getPersons();
        personList.removeIf(person -> (person.getId().equals(p.getId()))); 
    }

    public void addPerson(Person p) throws IOException, ParseException{
        List<Person> personList = getPersons();
        personList.add(p);
    }

    public void updatePerson(Person p) throws IOException, ParseException{
        deletePerson(p);
        addPerson(p);
    }

    public void saveData(Person p, String file) throws IllegalArgumentException, IllegalAccessException, IOException{

        String toSave = parsePerson(p);

        File dataFolder = new File(DataDir.dataFolder);
        
        if(!dataFolder.exists()){
            dataFolder.mkdir();
        }

        File filePath = new File(dataFolder, file);

        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(toSave);
        bw.newLine();

        bw.close();
        fw.close();

    }

    public void updateCSV(Person p, boolean toDelete) throws IOException, IllegalArgumentException, IllegalAccessException{

        String lineRead = "";
        File dataFolder = new File(DataDir.dataFolder);
        File filepath = new File(dataFolder, DataDir.filePath);
        File tempFile = new File(dataFolder, DataDir.tempFile);

        FileReader fr = new FileReader(filepath);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(tempFile, true);
        BufferedWriter bw = new BufferedWriter(fw);

        while ((lineRead = br.readLine())!=null){
            String[] terms = lineRead.split(",");
            String id = terms[0];

            if (!p.getId().equals(id)){
                bw.write(lineRead);
                bw.newLine();
            }
            else{
                if (toDelete){
                    continue;
                }
                else {
                    System.out.println(parsePerson(p));
                    saveData(p, DataDir.tempFile);
                }
            }
        }
        br.close();
        fr.close();
        bw.close();
        fw.close();

        filepath.delete();
        tempFile.renameTo(filepath);

    }

    public String parsePerson(Person p) throws IllegalArgumentException, IllegalAccessException{
        String toSave = p.getId()+","
                        +p.getFirstName()+","
                        +p.getLastName()+","
                        +p.getDateOfBirth().toString()+","
                        +p.getEmail()+","
                        +p.getPhoneNumber();
        
        return toSave;
    }
}
