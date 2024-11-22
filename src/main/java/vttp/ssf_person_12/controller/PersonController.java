package vttp.ssf_person_12.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import vttp.ssf_person_12.model.Person;
import vttp.ssf_person_12.service.PersonService;

@Controller
public class PersonController {
    
    private final static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    PersonService personService;

    @GetMapping("/")
    public String home(Model model){
        try {
            model.addAttribute("message", "A service for CRUD operations on persons.");
            return "home";
        } catch (Exception e) {
            logger.info("Error generating home page.");
            logger.error(e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Unable to load home page. Please try again later.");
        }
        return "errorPage";
    }

    @GetMapping("/persons")
    public String persons(Model model) throws IOException, ParseException{
        // this should display the list of persons by calling the person service to return the list of persons
        List<Person> personList = personService.getPersons();
        model.addAttribute("persons", personList);
        
        return "persons";
    }

    @PostMapping("/person/delete")
    public String deletePerson(@ModelAttribute(value="person") Person p, Model model) throws IOException, IllegalArgumentException, IllegalAccessException, ParseException{
        // the post request submits person attributes and binds these values to the Person p in this method
        personService.deletePerson(p);
        
        return "redirect:/persons";
    }

    @GetMapping("/person/update/{id}")
    public String updatePerson(@PathVariable String id, Model model) throws IOException, ParseException{
        
        Person p = personService.getPersonById(id);
        model.addAttribute("toUpdate", p);
        
        return "updateDetails";
    }

    @PostMapping("person/updateDetails")
    public String saveDetails(@Valid @ModelAttribute(value="toUpdate") Person p, BindingResult bindingResult, Model model) throws IllegalArgumentException, IllegalAccessException, IOException, ParseException{

        if (bindingResult.hasErrors()){
            return "updateDetails";
        }
        // when the user edits the fields and submits the form, the new entries will replace the old "toUpdate"
        // and binds it to the model, and the Person belonging to "toUpdate" key is passed here as a parameter 
    
        personService.updatePerson(p);
        
        List<Person> personList = personService.getPersons();
        model.addAttribute("persons", personList);
        return "redirect:/persons";
    }

    @GetMapping("/personForm")
    public String createPersonPage(Model model){
        // need to add a new Person object to the model upon this endpoint
        // because in the personForm.html, it is specified that the input data must be bound to a Person object named "person"
        model.addAttribute("person", new Person());
        return "personForm";
    }

    @PostMapping("/person/add")
    public String addPerson(@Valid @ModelAttribute(value="person") Person p, BindingResult bindingResult, Model model) throws IllegalArgumentException, IllegalAccessException, IOException, ParseException{
        // now that the personForm has been submitted with the corresponding fields, 
        // these attributes will bind to the "person" object in the model.
        if (bindingResult.hasErrors()){
            return "personForm";
        }

        personService.addPerson(p);
        
        List<Person> personList = personService.getPersons();
        model.addAttribute("persons", personList);
        return "redirect:/persons";
    }
}
