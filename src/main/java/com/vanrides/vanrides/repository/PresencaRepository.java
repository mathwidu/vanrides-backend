package com.vanrides.vanrides.repository;

import com.vanrides.vanrides.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {

    List<Presenca> findByPassageiroId(Long passageiroId);

    List<Presenca> findByDataPresenca(LocalDate dataPresenca);

    boolean existsByPassageiroIdAndDataPresenca(Long passageiroId, LocalDate dataPresenca);

    @Query("SELECT p FROM Presenca p WHERE p.dataPresenca = :data AND p.passageiro.id IN " +
            "(SELECT pa.id FROM Passageiro pa WHERE pa.motorista.id = :motoristaId)")
    List<Presenca> findByMotoristaIdAndData(@Param("motoristaId") Long motoristaId,
                                             @Param("data") LocalDate data);
}
