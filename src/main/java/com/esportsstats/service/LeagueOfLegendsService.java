package com.esportsstats.service;

import com.esportsstats.api.leagueoflegends.LeaguesAPI;
import com.esportsstats.api.leagueoflegends.ScheduleAPI;
import com.esportsstats.api.leagueoflegends.StandingsAPI;
import com.esportsstats.api.leagueoflegends.TournamentsAPI;
import com.esportsstats.model.leagueoflegends.LeaguesModel;
import com.esportsstats.model.leagueoflegends.ScheduleModel;
import com.esportsstats.model.leagueoflegends.StandingsModel;
import com.esportsstats.model.leagueoflegends.TournamentsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class LeagueOfLegendsService {

    private final URI ESPORTS_API_URI = URI.create("https://esports-api.lolesports.com/persisted/gw/");
    private final String ESPORTS_API_KEY = "0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z";
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public LeaguesAPI getLeagues(){
        URI uri = UriComponentsBuilder.fromUri(ESPORTS_API_URI)
                .path("getLeagues")
                .queryParam("hl", "en-GB")
                .build().toUri();

        String body = sendRequest(uri).body();
        LeaguesModel leaguesModel = objectMapper.readValue(body, LeaguesModel.class);

        return new LeaguesAPI(leaguesModel);
    }

    @SneakyThrows
    public ScheduleAPI getSchedule(String leagueId, String pageToken){
        URI uri = UriComponentsBuilder.fromUri(ESPORTS_API_URI)
                .path("getSchedule")
                .queryParam("hl", "en-GB")
                .queryParam("leagueId", leagueId)
                .queryParam("pageToken", pageToken)
                .build().toUri();

        String body = sendRequest(uri).body();
        ScheduleModel scheduleModel = objectMapper.readValue(body, ScheduleModel.class);


        return new ScheduleAPI(scheduleModel);
    }

    @SneakyThrows
    public StandingsAPI getStandings(String leagueId) {
        TournamentsAPI tournament = getTournamentId(leagueId);

        URI uri = UriComponentsBuilder.fromUri(ESPORTS_API_URI)
                .path("getStandings")
                .queryParam("hl", "en-GB")
                .queryParam("tournamentId", tournament.currentTournament.id())
                .build().toUri();

        String body = sendRequest(uri).body();
        StandingsModel standingsModel = objectMapper.readValue(body, StandingsModel.class);

        return new StandingsAPI(tournament.currentTournament.slug(), standingsModel);
    }

    @SneakyThrows
    private TournamentsAPI getTournamentId(String leagueId){
        URI uri = UriComponentsBuilder.fromUri(ESPORTS_API_URI)
                .path("getTournamentsForLeague")
                .queryParam("hl", "en-GB")
                .queryParam("leagueId", leagueId)
                .build().toUri();

        String body = sendRequest(uri).body();
        TournamentsModel tournamentsModel = objectMapper.readValue(body, TournamentsModel.class);

        return new TournamentsAPI(tournamentsModel);
    }

    @SneakyThrows
    private HttpResponse<String> sendRequest(URI uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("x-api-key", ESPORTS_API_KEY)
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }
}
