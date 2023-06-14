package com.devsuperior.dslist.repositories;

import com.devsuperior.dslist.entities.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class Repositorio {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deletar (Game game) {
        this.entityManager.remove(game);
    }
}
