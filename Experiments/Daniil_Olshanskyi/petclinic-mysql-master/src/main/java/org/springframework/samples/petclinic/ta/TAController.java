package org.springframework.samples.petclinic.ta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TAController {

    @Autowired
    TARepository taRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/tas")
    public List<Ta> getAllTAs() {
        return taRepository.findAll();
    }
}
