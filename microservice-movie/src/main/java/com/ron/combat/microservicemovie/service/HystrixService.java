package com.ron.combat.microservicemovie.service;

import java.util.List;
import java.util.concurrent.Future;

public interface HystrixService {

    public Future<String> combineAnnotation(long id);

    public List<String> query(List<Long> ids);
}
