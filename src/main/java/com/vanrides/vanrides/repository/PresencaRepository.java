package com.vanrides.vanrides.repository;

import com.vanrides.vanrides.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {

    List<Presenca> findByPassageiroId(Long passageiroId);

    List<Presenca> findByDataPresenca(LocalDate dataPresenca);

    // ✅ NOVO: busca para impedir duplicidade
    List<Presenca> findByPassageiroIdAndDataPresenca(Long passageiroId, LocalDate dataPresenca);
}
