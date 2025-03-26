package com.williamsilva.algafoodapi.infrastructure;

import com.williamsilva.algafoodapi.domain.model.Cozinha;
import com.williamsilva.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    @Override
    public Cozinha porId(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Override
    public List<Cozinha> listar() {
        return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = manager.find(Cozinha.class, id);

        if (cozinha == null) {
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(cozinha);
    }
}
