package com.kopwan.dao.impl;

import com.kopwan.dao.SimpananRepositoryCustom;
import com.kopwan.dao.util.RepositoryUtil;
import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.request.param.SimpananParamRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SimpananRepositoryImpl implements SimpananRepositoryCustom {
    @Autowired
    private RepositoryUtil repositoryUtil;

    @Override
    public Mono<Page<Simpanan>> filterSimpanan(SimpananParamRequest request) {
        Query query = new Query();
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1,
                request.getSize());

        query.addCriteria(Criteria.where("markForDelete").is(false));
        if (StringUtils.isNotBlank(request.getType())) {
            query.addCriteria(Criteria.where("type")
                    .is(request.getType()));
        }
        if(request.getRw() != 0){
            query.addCriteria(Criteria.where("anggota.rw")
                    .is(request.getRw()));
        }
        if(request.getNo() != 0){
            query.addCriteria(Criteria.where("anggota.no")
                    .is(request.getNo()));
        }
        if (StringUtils.isNotBlank(request.getName())) {
            query.addCriteria(Criteria.where("anggota.name")
                    .regex(request.getName(), "i"));
        }
        if (request.getMonth() != 0) {
            query.addCriteria(Criteria.where("month")
                    .is(request.getMonth()));
        }
        if(request.getYear() != 0){
            query.addCriteria(Criteria.where("year")
                    .is(request.getYear()));
        }
        if (request.getMonth() != 0 || request.getYear() != 0) {
            query.with(Sort.by(Sort.Direction.ASC, "anggota.rw"));
        }
        return repositoryUtil.findAllPageable(query, Simpanan.class, pageRequest);
    }
}
