package com.esportsstats.model.leagueoflegends;

import java.util.Date;
import java.util.List;

public class TournamentsModel {
    public Leagues data;

    public record Leagues(
            List<League> leagues
    ){}

    public record League (
            List<Tournament> tournaments
    ){}

    public record Tournament (
            String id,
            String slug,
            Date startDate,
            Date endDate
    ){}
}
