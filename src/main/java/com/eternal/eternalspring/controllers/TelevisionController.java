// author Omur Culhaci

package com.eternal.eternalspring.controllers;

import com.eternal.eternalspring.exceptions.RecordNotFoundException;
import com.eternal.eternalspring.exceptions.RecordTooLongException;
import com.eternal.eternalspring.model.Television;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TelevisionController {

    private List<Television> televisions;
    //Constructor
    public TelevisionController(List<Television> televisions) {
        this.televisions = televisions;
    }

    //Get request voor alle TVs
    @GetMapping("/televisions")
    public ResponseEntity<List<Television>> getTvs() {
        return new ResponseEntity<>(televisions, HttpStatus.OK);
    }

    //Get request voor 1 TV op {index} positie
    @GetMapping("/televisions/{index}")
    public ResponseEntity<Television> getTv(@PathVariable int index) {
        if (index >= 0 && index < televisions.size()) {
            Television tv = televisions.get(index);
            return new ResponseEntity<>(tv, HttpStatus.OK);
        } else {
            throw new RecordNotFoundException("TV niet gevonden!");
        }
    }
    //Bonus bonus bonus :)
    //Post request voor 1 TV.
    //Is het mogelijk om meerdere TV's in één keer toe te voegen met JSON?
    //Ik krijg een error (400 Bad Request) als ik het probeer. En ik kon het ook niet vangen met try/catch.
    @PostMapping("/televisions")
    public ResponseEntity<Television> addTv(@RequestBody Television newTelevision) {
        if (newTelevision.getBrand().length() < 20) {
            televisions.add(newTelevision);
            return new ResponseEntity<>(newTelevision, HttpStatus.CREATED);
        } else {
            throw new RecordTooLongException("Merk van de TV dient uit maximaal 20 karakters te bestaan.");
        }
    }

    //Put request met custom error handling.
    //Vervangt de TV op {index} positie.
    @PutMapping("/televisions/{index}")
    public ResponseEntity<Television> updateTV(@PathVariable int index, @RequestBody Television television) {
        if (index >= 0 && index < televisions.size()) {
            televisions.remove(index);
            televisions.add(index, television);
            return new ResponseEntity<>(television, HttpStatus.NO_CONTENT);
        } else {
            throw new RecordNotFoundException("TV niet gevonden!");
        }
    }

    //Delete request met custom error handling.
    //Verwijdert de TV op {index} positie
    @DeleteMapping("/televisions/{index}")
    public ResponseEntity<Television> deleteTv(@PathVariable int index) {
        if (index >= 0 && index < televisions.size()) {
            televisions.remove(index);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            throw new RecordNotFoundException("TV niet gevonden!");
        }
    }

}
