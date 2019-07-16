package com.bydrecchallenge.myapplication.interactors;

import com.bydrecchallenge.myapplication.interfaces.ApiRetrofitService;
import com.bydrecchallenge.myapplication.models.BydrecData;
import com.bydrecchallenge.myapplication.models.MatchesInformation;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;

public class BydrecDataInteractor {

    private final ApiRetrofitService apiRetrofitService;

    @Inject
    public BydrecDataInteractor(ApiRetrofitService apiRetrofitService) {
        this.apiRetrofitService = apiRetrofitService;
    }

    public interface BydrecInteractorListener {
        void onBydrecFixtureDataSuccess(MatchesInformation matchesInformation);
        void onBydrecResultDataSuccess(MatchesInformation matchesInformation);
        void onBydrecDataFailure();
    }

    public void getFixturesBydrecData(final BydrecInteractorListener listener) {
        apiRetrofitService.getFixtures().enqueue(new Callback<List<BydrecData>>() {
            @Override
            public void onResponse(@NotNull Call<List<BydrecData>> call, @NotNull Response<List<BydrecData>> response) {
               if (response.isSuccessful()) {
                   listener.onBydrecFixtureDataSuccess(new MatchesInformation(response.body()));
                } else {
                    listener.onBydrecDataFailure();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<BydrecData>> call, @NotNull Throwable t) {
                listener.onBydrecDataFailure();
            }
        });
    }

    public void getResultsBydrecData(final BydrecInteractorListener listener) {
        apiRetrofitService.getResults().enqueue(new Callback<List<BydrecData>>() {
            @Override
            public void onResponse(@NotNull Call<List<BydrecData>> call, @NotNull Response<List<BydrecData>> response) {
                if (response.isSuccessful()) {
                    listener.onBydrecResultDataSuccess(new MatchesInformation(response.body()));
                } else {
                    listener.onBydrecDataFailure();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<BydrecData>> call, @NotNull Throwable t) {
                listener.onBydrecDataFailure();
            }
        });
    }



}
