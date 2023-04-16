package com.esportsstats.api.leagueoflegends;

import com.esportsstats.model.leagueoflegends.StandingsModel;

import java.util.LinkedList;
import java.util.List;

public class StandingsAPI {
    public Standings standings;

    public StandingsAPI(String slug, StandingsModel standingsModel) {
        List<Stage> stages = new LinkedList<>();

        for(var standing: standingsModel.data.standings()){
            for(var stage: standing.stages())
                stages.add(new Stage(
                        stage.name(),
                        stage.type(),
                        stage.slug(),
                        toAPISections(stage.sections())
                ));
        }

        this.standings = new Standings(stages, slug);
    }

    private List<Section> toAPISections(List<StandingsModel.Section> sections){
        List<Section> sectionsAPI = new LinkedList<>();

        for(var section: sections){
            sectionsAPI.add(new Section(
                    section.name(),
                    toAPIMatchs(section.matches()),
                    toAPIRankings(section.rankings())
            ));
        }

        return sectionsAPI;
    }

    private List<Match> toAPIMatchs(List<StandingsModel.Match> matches) {
        List<Match> matchesAPI = new LinkedList<>();

        for(var match: matches){
            matchesAPI.add(new Match(
                    match.id(),
                    match.state(),
                    match.previousMatchIds(),
                    toAPI(match.teams().get(0)),
                    toAPI(match.teams().get(1))
            ));
        }

        return matchesAPI;
    }

    private List<Ranking> toAPIRankings(List<StandingsModel.Ranking> rankings) {
        List<Ranking> rankingsAPI = new LinkedList<>();

        for(var ranking: rankings){
            rankingsAPI.add(new Ranking(
                    ranking.ordinal(),
                    toAPI(ranking.teams())
            ));
        }

        return rankingsAPI;
    }

    private List<Team> toAPI(List<StandingsModel.Team> teams) {
        List<Team> teamsAPI = new LinkedList<>();

        for(var team: teams){
            teamsAPI.add(toAPI(team));
        }

        return teamsAPI;
    }

    private Team toAPI(StandingsModel.Team team) {
        return new Team(
                team.id(),
                team.name(),
                team.code(),
                team.image(),
                toAPI(team.result()),
                toAPI(team.record())
        );
    }

    private Result toAPI(StandingsModel.Result result){
        if(result == null || result.outcome() == null){
            return new Result("", 0);
        }
        return new Result(result.outcome(), result.gameWins());
    }

    private Record toAPI(StandingsModel.Record record){
        if(record == null){
            return new Record(0, 0);
        }
        return new Record(record.wins(), record.losses());
    }

    public record Standings(List<Stage> stages, String slug){}

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
            Team team1,
            Team team2
    ){}

    public record Ranking (
            int ordinal,
            List<Team> teams
    ){}

    public record Team(String id, String name, String code, String img, Result result, Record record){}

    public record Result (String outcome, int gameWins){}

    public record Record(int wins, int losses) {}
}
