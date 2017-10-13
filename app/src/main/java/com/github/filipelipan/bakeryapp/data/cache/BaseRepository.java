package com.github.filipelipan.bakeryapp.data.cache;

import com.github.filipelipan.bakeryapp.data.ws.RestApi;

import java.io.File;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by lispa9 on 08/08/17.
 */

public abstract class BaseRepository {

    final RestApi restApi;
    final Providers providers;

    BaseRepository(File cacheDir,  RestApi restApi) {
        this.restApi = restApi;
        this.providers = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(Providers.class);
    }

}
