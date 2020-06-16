package com.kopwan.service;

import com.kopwan.dao.NeracaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeracaService {
    @Autowired
    private NeracaRepository neracaRepository;
}
