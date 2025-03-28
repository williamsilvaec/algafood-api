package com.williamsilva.algafoodapi.domain.repository;

import com.williamsilva.algafoodapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteQueries {

//    @Query("select r from Restaurante r where r.nome like %:nome% and r.cozinha.id = :id")
    List<Restaurante> consultaPorNome(String nome, @Param("id") Long cozinhaId);


}
