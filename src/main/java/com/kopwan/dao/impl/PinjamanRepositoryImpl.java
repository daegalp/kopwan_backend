package com.kopwan.dao.impl;

import com.kopwan.dao.PinjamanRepositoryCustom;
import com.kopwan.dao.util.RepositoryUtil;
import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.request.param.PinjamanParamRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class PinjamanRepositoryImpl implements PinjamanRepositoryCustom {
    @Autowired
    private RepositoryUtil repositoryUtil;

    @Override
    public Mono<Page<Pinjaman>> filterPinjaman(PinjamanParamRequest request) {
        Query query = new Query();
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1,
                request.getSize());

        query.addCriteria(Criteria.where("markForDelete").is(false));
        if (StringUtils.isNotBlank(request.getMonth())) {
            query.addCriteria(Criteria.where("month")
                    .is(request.getMonth()));
        }
        if(request.getRw() != 999){
            query.addCriteria(Criteria.where("anggota.rw")
                    .is(request.getRw()));
        }
        if(request.getNo() != 999){
            query.addCriteria(Criteria.where("anggota.no")
                    .is(request.getNo()));
        }
        if (StringUtils.isNotBlank(request.getName())) {
            query.addCriteria(Criteria.where("anggota.name")
                    .regex(request.getName(), "i"));
        }
        if(request.getYear() != 999){
            query.addCriteria(Criteria.where("year")
                    .is(request.getYear()));
        }
        if (StringUtils.isNotBlank(request.getMonth()) || request.getYear() != 999) {
            query.with(Sort.by(Sort.Direction.ASC, "anggota.rw"));
        }
        return repositoryUtil.findAllPageable(query, Pinjaman.class, pageRequest);
    }
}
