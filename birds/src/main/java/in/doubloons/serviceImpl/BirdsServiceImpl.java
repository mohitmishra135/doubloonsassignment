package in.doubloons.serviceImpl;

import java.util.List;

import in.doubloons.dao.BirdsDao;
import in.doubloons.dto.BirdsDto;
import in.doubloons.dto.BirdsResponse;
import in.doubloons.service.BirdsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BirdsServiceImpl implements BirdsService{
	@Autowired
	private BirdsDao birdDao;
	
	public List<BirdsDto> listAllBirds(){
		return birdDao.listAllBirds();
	}

	public BirdsResponse addBirds(BirdsDto bird) {
		
		return birdDao.addBirds(bird);
	}

	public BirdsDto getBirdById(String birdId) {
		
		return birdDao.getBirdById(birdId);
	}

	public BirdsResponse deleteBird(String birdId) {
		
		return birdDao.deleteBird(birdId);
	}
	
}

