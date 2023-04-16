package com.esportsstats.controller;

import com.esportsstats.api.leagueoflegends.LeaguesAPI;
import com.esportsstats.api.leagueoflegends.ScheduleAPI;
import com.esportsstats.api.leagueoflegends.StandingsAPI;
import com.esportsstats.service.LeagueOfLegendsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/lol")
public class LeagueOfLegendsController {

    public final LeagueOfLegendsService leagueOfLegendsService;

    public LeagueOfLegendsController(LeagueOfLegendsService leagueOfLegendsService) {
        this.leagueOfLegendsService = leagueOfLegendsService;
    }

    @GetMapping(path = "/leagues")
    public LeaguesAPI getLeagues(){
        return leagueOfLegendsService.getLeagues();
    }

    @GetMapping(path = "/schedule")
    public ScheduleAPI getSchedule(@RequestParam String leagueId, @RequestParam(required = false) String pageToken){
        return leagueOfLegendsService.getSchedule(leagueId, pageToken);
    }

    @GetMapping(path = "/standings")
    public StandingsAPI getStandings(@RequestParam String leagueId){
        return leagueOfLegendsService.getStandings(leagueId);
    }
}
