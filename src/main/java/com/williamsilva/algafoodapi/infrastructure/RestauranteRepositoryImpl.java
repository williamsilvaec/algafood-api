package com.williamsilva.algafoodapi.infrastructure;

import com.williamsilva.algafoodapi.domain.model.Restaurante;
import com.williamsilva.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Restaurante porId(Long id) {
        return manager.find(Restaurante.class, id);
    }

    @Transactional
    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Override
    public List<Restaurante> listar() {
        return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Restaurante restaurante = porId(id);

        if (restaurante == null) {
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(restaurante);
    }
}
