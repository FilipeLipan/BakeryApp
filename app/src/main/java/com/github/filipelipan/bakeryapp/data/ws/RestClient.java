package com.github.filipelipan.bakeryapp.data.ws;

import com.github.filipelipan.bakeryapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lispa on 12/10/2017.
 */

public class RestClient {

	private RestApi mApi;

	private Retrofit mRetrofitClient;

	private OkHttpClient mHttpClient;

	public RestClient() {

		if (BuildConfig.DEBUG) {
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

			mHttpClient = new OkHttpClient.Builder()
					//Http authorize interceptor
					.addInterceptor(loggingInterceptor)
					.build();
		} else {
			mHttpClient = new OkHttpClient.Builder()
					.build();
		}

		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				.create();

		mRetrofitClient = new Retrofit.Builder()
				.baseUrl(BuildConfig.HOST)
				.client(mHttpClient)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();

		mApi = mRetrofitClient.create(RestApi.class);
	}

	public RestApi getApi() {
		return mApi;
	}
}
