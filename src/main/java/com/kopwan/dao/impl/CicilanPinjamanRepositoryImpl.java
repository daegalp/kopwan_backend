package com.kopwan.dao.impl;

import com.kopwan.dao.CicilanPinjamanRepositoryCustom;
import com.kopwan.dao.util.RepositoryUtil;
import com.kopwan.model.entity.CicilanPinjaman;
import com.kopwan.model.request.param.CicilanPinjamanParam;
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
public class CicilanPinjamanRepositoryImpl implements CicilanPinjamanRepositoryCustom {
    @Autowired
    private RepositoryUtil repositoryUtil;

    @Override
    public Mono<Page<CicilanPinjaman>> filterCicilanPinjaman(CicilanPinjamanParam request) {
        Query query = new Query();
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1,
                request.getSize());

        query.addCriteria(Criteria.where("markForDelete").is(false));
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
        if (request.getMonth() != 999) {
            query.addCriteria(Criteria.where("month")
                    .is(request.getMonth()));
        }
        if(request.getYear() != 999){
            query.addCriteria(Criteria.where("year")
                    .is(request.getYear()));
        }
        if (request.getMonth() != 999 || request.getYear() != 999) {
            query.with(Sort.by(Sort.Direction.ASC, "anggota.rw"));
        }
        return repositoryUtil.findAllPageable(query, CicilanPinjaman.class, pageRequest);
    }
}
