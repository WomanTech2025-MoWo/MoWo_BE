package com.womantech.mowo.domain.knowhow.service;

import com.womantech.mowo.domain.knowhow.converter.KnowhowConverter;
import com.womantech.mowo.domain.knowhow.dto.KnowhowResponseListDTO;
import com.womantech.mowo.domain.knowhow.entity.Knowhow;
import com.womantech.mowo.domain.knowhow.repository.KnowhowRepository;
import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.global.security.service.AuthorizationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KnowhowService {

    private final KnowhowRepository knowhowRepository;
    private final KnowhowConverter knowhowConverter;
    private final AuthorizationService authorizationService;

    public KnowhowService(KnowhowRepository knowhowRepository, KnowhowConverter knowhowConverter, AuthorizationService authorizationService) {
        this.knowhowRepository = knowhowRepository;
        this.knowhowConverter = knowhowConverter;
        this.authorizationService = authorizationService;
    }

    public Members ensureAdminOrThrow(Long userId) {
        return authorizationService.ensureAdminOrThrow(userId);
    }

    public List<KnowhowResponseListDTO> getAll() {
        return knowhowRepository.findAll().stream()
                .map(knowhowConverter::toListDTO)
                .toList();
    }

    public Optional<Knowhow> getById(Long id) {
        return knowhowRepository.findByIdWithTodos(id);
    }

    public Knowhow create(Knowhow knowhow) {
        return knowhowRepository.save(knowhow);
    }

    public Knowhow update(Knowhow knowhow) {
        return knowhowRepository.save(knowhow);
    }

    public void deleteById(Long id) {
        knowhowRepository.deleteById(id);
    }
}
