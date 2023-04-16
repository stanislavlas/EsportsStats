package com.esportsstats.model.leagueoflegends;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class ScheduleModel {
    public ScheduleData data;

    public record ScheduleData (Schedule schedule) {}

    public record Schedule (List<Event> events, Pages pages){}

    public record Event (
            Date startTime,
            String state,
            String type,
            String blockName,
            League league,
            Match match
    ){}

    public record Pages(String older, String newer){}

    public record League (String name, String slug, @JsonIgnore String image){}

    public record Match (String id, List<Team> teams, Strategy strategy, List<String> flags){}

    public record Strategy (String type, int count){}

    public record Team (String name, String code, String image, Result result, Record record){}

    public record Result (String outcome, int gameWins){}

    public record Record (int wins, int losses){}
}
