package com.vanrides.vanrides.repository;

import com.vanrides.vanrides.model.LocalizacaoMotorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalizacaoMotoristaRepository extends JpaRepository<LocalizacaoMotorista, Long> {

    List<LocalizacaoMotorista> findByMotoristaIdOrderByDataHoraDesc(Long motoristaId);
}
