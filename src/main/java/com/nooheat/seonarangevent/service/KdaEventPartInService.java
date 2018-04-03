package com.nooheat.seonarangevent.service;

import com.nooheat.seonarangevent.domain.partIn.kda.KdaEventPartInRepository;
import com.nooheat.seonarangevent.dto.partIn.kda.KdaEventPartInDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@NoArgsConstructor
public class KdaEventPartInService {

    @Autowired
    private KdaEventPartInRepository kdaEventPartInRepository;

    @Transactional
    public void save(KdaEventPartInDto kdaEventPartInDto) {
        kdaEventPartInRepository.save(kdaEventPartInDto.toEntity());
    }
}
