package com.esportsstats.model.leagueoflegends;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class StandingsModel {
    public StandingsData data;

    public record StandingsData(List<Standings> standings){}

    public record Standings(List<Stage> stages){}

    public record Stage(
            String name,
            String type,
            String slug,
            List<Section> sections
    ){}

    public record Section(
            String name,
            List<Match> matches,
            List<Ranking> rankings
    ){}

    public record Match(
             String id,
             String state,
             String previousMatchIds,
             @JsonIgnore int flags,
             List<Team> teams
    ){}

    public record Ranking(
            int ordinal,
            List<Team> teams
    ){}

    public record Team(
            String id,
            String slug,
            String name,
            String code,
            String image,
            Record record,
            Result result
    ){}

    public record Record (
           int wins,
           int losses
    ){}

    public record Result (
            String outcome,
            int gameWins
    ){}
}
