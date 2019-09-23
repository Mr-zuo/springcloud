package com.ron.combat.microservicemovie.service;

import rx.Observable;

import java.util.concurrent.ExecutionException;

public interface ConditionService {

    public Observable<String> helloService() throws ExecutionException, InterruptedException;
}
