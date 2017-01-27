package in.doubloons.service;

import in.doubloons.dto.BirdsDto;
import in.doubloons.dto.BirdsResponse;

import java.util.List;

public interface BirdsService {
	public List<BirdsDto> listAllBirds();
	public BirdsResponse addBirds(BirdsDto bird);
	public BirdsDto getBirdById(String birdId);
	public BirdsResponse deleteBird(String birdId);
}
