package com.vanrides.vanrides.repository;

import com.vanrides.vanrides.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    // Método para buscar motorista pelo e-mail
    Motorista findByEmail(String email);
}
