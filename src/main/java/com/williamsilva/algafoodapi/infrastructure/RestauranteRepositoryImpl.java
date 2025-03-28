package com.williamsilva.algafoodapi.infrastructure;

import com.williamsilva.algafoodapi.domain.model.Restaurante;
import com.williamsilva.algafoodapi.domain.repository.RestauranteQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RestauranteRepositoryImpl implements RestauranteQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> filtrarPor(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("from Restaurante where 0 = 0 ");

        Map<String, Object> params = new HashMap<>();

        if (StringUtils.hasText(nome)) {
            jpql.append(" and nome like :nome ");
            params.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append(" and taxaFrete >= :taxaFreteInicial ");
            params.put("taxaFreteInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append(" and taxaFrete <= :taxaFreteFinal ");
            params.put("taxaFreteFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
