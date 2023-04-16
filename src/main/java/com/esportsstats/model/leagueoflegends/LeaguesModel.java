package com.esportsstats.model.leagueoflegends;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class LeaguesModel {
    public Leagues data;

    public record Leagues(
        List<League> leagues
    ){}

    public record League (
            String id,
            String slug,
            String name,
            String region,
            String image,
            int priority,
            @JsonIgnore int displayPriority
    ){}
}
