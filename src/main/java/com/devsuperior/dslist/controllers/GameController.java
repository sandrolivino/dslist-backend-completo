package com.devsuperior.dslist.controllers;

import java.util.List;
import java.util.UUID;

import com.devsuperior.dslist.entities.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.services.GameService;

@RestController
@RequestMapping(value = "/games")
public class GameController {

	@Autowired
	private GameService gameService;	


	@GetMapping(value = "/{id}")
	@CrossOrigin(origins = "*")
	public GameDTO findById(@PathVariable UUID id) {
		GameDTO result = gameService.findById(id);
		return result;
	}


	@GetMapping
	@CrossOrigin(origins = "*")
	public List<GameDTO> findAll() {
		List<GameDTO> result = gameService.findAll();
		return result;
	}

	@PostMapping
    @CrossOrigin(origins = "*")
    public GameDTO createGame(@RequestBody GameDTO dto) {
		GameDTO result = gameService.save(dto);
		return result;
	}

	@DeleteMapping(value = "/{id}")
	@CrossOrigin(origins = "*")
	public void deleteGame(@PathVariable UUID id) {
		gameService.delete(id);
	}

	// Create method for updating a game
	@PutMapping(value = "/{id}")
    @CrossOrigin(origins = "*")
    public void updateGame(@PathVariable UUID id, @RequestBody GameDTO dto) {
		gameService.update(id, dto);
    }
}
