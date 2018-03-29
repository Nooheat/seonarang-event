package com.nooheat.seonarangevent.domain.partIn.kda;

import com.nooheat.seonarangevent.domain.partIn.EventPartInId;
import com.nooheat.seonarangevent.domain.partIn.kda.KdaEventPartIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KdaEventPartInRepository extends JpaRepository<KdaEventPartIn, EventPartInId> {
}
