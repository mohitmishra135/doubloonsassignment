package in.doubloons.dao;

import in.doubloons.dto.BirdsDto;
import in.doubloons.dto.BirdsResponse;

import java.util.List;

public interface BirdsDao {
	public List<BirdsDto> listAllBirds();
	public BirdsResponse addBirds(BirdsDto bird);
	public BirdsDto getBirdById(String birdId);
	public BirdsResponse deleteBird(String birdId);
}
