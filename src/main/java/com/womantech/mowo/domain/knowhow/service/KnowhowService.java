package com.womantech.mowo.domain.knowhow.service;

import com.womantech.mowo.domain.knowhow.converter.KnowhowConverter;
import com.womantech.mowo.domain.knowhow.dto.KnowhowResponseListDTO;
import com.womantech.mowo.domain.knowhow.entity.Knowhow;
import com.womantech.mowo.domain.knowhow.repository.KnowhowRepository;
import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.member.repository.MemberRepository;
import com.womantech.mowo.global.apiPayload.code.status.ErrorStatus;
import com.womantech.mowo.global.apiPayload.exception.handler.MemberHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KnowhowService {

    private final KnowhowRepository knowhowRepository;
    private final KnowhowConverter knowhowConverter;
    private final MemberRepository memberRepository;


    public KnowhowService(KnowhowRepository knowhowRepository, KnowhowConverter knowhowConverter, MemberRepository memberRepository) {
        this.knowhowRepository = knowhowRepository;
        this.knowhowConverter = knowhowConverter;
        this.memberRepository = memberRepository;
    }

    public Members getMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public Members ensureAdminOrThrow(Long userId) {
        Members m = getMemberOrThrow(userId);
        String role = String.valueOf(m.getRole());
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new MemberHandler(ErrorStatus._FORBIDDEN);
        }
        return m;
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
