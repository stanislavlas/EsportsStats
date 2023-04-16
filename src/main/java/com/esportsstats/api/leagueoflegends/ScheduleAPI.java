package com.esportsstats.api.leagueoflegends;

import com.esportsstats.model.leagueoflegends.ScheduleModel;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ScheduleAPI {
    public Schedule schedule;

    public ScheduleAPI(ScheduleModel scheduleModel) {
        List<Match> matches = new LinkedList<>();
        Date now = new Date();

        for(var modelEvent: scheduleModel.data.schedule().events()){
            if(modelEvent.startTime().after(now))
                matches.add(new Match(
                    modelEvent.match().id(),
                    modelEvent.startTime(),
                    modelEvent.state(),
                    modelEvent.type(),
                    modelEvent.blockName(),
                    toAPI(modelEvent.league()),
                    modelEvent.match().flags(),
                    toAPI(modelEvent.match().teams().get(0)),
                    toAPI(modelEvent.match().teams().get(1)),
                    toAPI(modelEvent.match().strategy())
            ));
        }

        schedule = new Schedule(matches);
    }

    private League toAPI(ScheduleModel.League league){
        return new League(league.name(), league.slug());
    }

    private Team toAPI(ScheduleModel.Team team){
        return new Team(
                team.name(),
                team.code(),
                team.image(),
                toAPI(team.result()),
                toAPI(team.record()));
    }

    private Strategy toAPI(ScheduleModel.Strategy strategy){
        return new Strategy(strategy.type(), strategy.count());
    }

    private Result toAPI(ScheduleModel.Result result){
        if(result == null || result.outcome() == null){
            return new Result("", 0);
        }
        return new Result(result.outcome(), result.gameWins());
    }

    private Record toAPI(ScheduleModel.Record record){
        if(record == null){
            return new Record(0, 0);
        }
        return new Record(record.wins(), record.losses());
    }

    public record Schedule(List<Match> matches){}

    public record Match(
            String id,
            Date startTime,
            String state,
            String type,
            String blockName,
            League league,
            List<String> flags,
            Team team1,
            Team team2,
            Strategy strategy
    ){}

    public record League (String name, String slug){}

    public record Team(String name, String code, String img, Result result, Record record){}

    public record Strategy(String type, int count){}

    public record Result (String outcome, int gameWins){}

    public record Record(int wins, int losses) {}
}
