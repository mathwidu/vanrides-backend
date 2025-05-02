package com.vanrides.vanrides.repository;

import com.vanrides.vanrides.model.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassageiroRepository extends JpaRepository<Passageiro, Long> {

    // Podemos criar métodos personalizados aqui depois, como buscar por e-mail

    Passageiro findByEmail(String email);
}
