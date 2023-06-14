package com.devsuperior.dslist.services;

import java.util.List;
import java.util.UUID;

import com.devsuperior.dslist.exceptions.ResourceNotFoundException;
import com.devsuperior.dslist.repositories.Repositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private Repositorio gameRepositorio;

    @Transactional(readOnly = true)
    public GameDTO findById(@PathVariable UUID id) {
        Game result = gameRepository.findById(id).get();
        return new GameDTO(result);
    }

    @Transactional(readOnly = true)
    public List<GameDTO> findAll() {
        List<Game> result = gameRepository.findAll();
        return result.stream().map(GameDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByGameList(Long listId) {
        List<GameMinProjection> games = gameRepository.searchByList(listId);
        return games.stream().map(GameMinDTO::new).toList();
    }

    @Transactional
    public GameDTO save(GameDTO gameDTO) {
        Game entity = new Game();
        BeanUtils.copyProperties(gameDTO, entity);
        gameRepository.save(entity);
        return new GameDTO(entity);
    }

    @Transactional
    public void delete(UUID id) throws ResourceNotFoundException {
         Game game = gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found!"));

        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("Game");

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Game game = em.find(Game.class, id);

            if (game != null) {
                em.remove(game);
                em.getTransaction().commit();
                System.out.println("Jogo excluído");
            } else {
                System.out.println("Jogo não encontrado");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Ocorreu um erro ao excluir o jogo");
        } finally {
            em.close();
        }*/

        gameRepositorio.deletar(game);
    }

    @Transactional
    public void update(UUID id, GameDTO dto) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found!"));

        if (game != null) {
            game.setGenre(dto.getGenre());
            game.setImgUrl(dto.getImgUrl());
            game.setLongDescription(dto.getLongDescription());
            game.setShortDescription(dto.getShortDescription());
            game.setTitle(dto.getTitle());
            game.setYear(dto.getYear());
            game.setScore(dto.getScore());
            game.setPrice(dto.getPrice());
            game.setPlatforms(dto.getPlatforms());
            game.setInventoryStatus(dto.getInventoryStatus());
            gameRepository.save(game);
            System.out.println("Jogo atualizado");
        }
        else {
            throw new ResourceNotFoundException("Game not found!");
        }
    }
}
