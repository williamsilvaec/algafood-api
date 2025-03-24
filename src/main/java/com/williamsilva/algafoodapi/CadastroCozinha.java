package com.williamsilva.algafoodapi;

import com.williamsilva.algafoodapi.domain.model.Cozinha;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar() {
        return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    public Cozinha buscarCozinha(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    public void excluir(Cozinha cozinha) {
        cozinha = buscarCozinha(cozinha.getId());
        manager.remove(cozinha);
    }
}
