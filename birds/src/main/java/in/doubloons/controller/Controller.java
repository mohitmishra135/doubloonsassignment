package in.doubloons.controller;

import java.util.List;

import in.doubloons.dto.BirdsDto;
import in.doubloons.dto.BirdsResponse;
import in.doubloons.service.BirdsService;







import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class Controller {
	@Autowired
	BirdsService birdService;
	@RequestMapping(method=RequestMethod.GET, value="/get", produces="application/json")
	public List<BirdsDto> getAllBirds(HttpServletRequest request) {
		
		return birdService.listAllBirds();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/get/{id}", produces="application/json")
	public BirdsDto getBirdById(@PathVariable("id") String birdId){
		
		return birdService.getBirdById(birdId);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/delete/{id}", produces="application/json")
	public ResponseEntity<?> deleteBirdById(@PathVariable("id") String birdId){
		
		BirdsResponse birdResponse= birdService.deleteBird(birdId);
		if(birdResponse.getBirdsMessage().equals("Bird doesnot exist")){
			return new ResponseEntity<BirdsResponse>(birdResponse, HttpStatus.NOT_FOUND);
		}
		else{
			return new ResponseEntity<BirdsResponse>(birdResponse, HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/addBird", produces="application/json")
	public ResponseEntity<?> addBird(@RequestBody BirdsDto bird){
		BirdsResponse birdResponse= birdService.addBirds(bird);
		if(birdResponse.getBirdsMessage().equals("yes")){
			return new ResponseEntity<BirdsResponse>(birdResponse, HttpStatus.CREATED);
		}
		else{
			return new ResponseEntity<BirdsResponse>(birdResponse, HttpStatus.BAD_REQUEST);
		}
	}
}