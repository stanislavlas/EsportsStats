package com.esportsstats.api.leagueoflegends;

import com.esportsstats.model.leagueoflegends.TournamentsModel;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TournamentsAPI {
    List<Tournament> tournaments = new LinkedList<>();
    public Tournament currentTournament;

    public TournamentsAPI(TournamentsModel tournamentsModel){
        Date now = new Date();
        Tournament tournamentModel;

        for(var tournament : tournamentsModel.data.leagues().get(0).tournaments()){
            tournamentModel = new Tournament(
                    tournament.id(),
                    tournament.slug(),
                    tournament.startDate(),
                    tournament.endDate()
            );

            if(tournamentModel.startDate.before(now) && tournamentModel.endDate.after(now)){
                currentTournament = tournamentModel;
            }

            tournaments.add(tournamentModel);
        }
    }

    public record Tournament(
            String id,
            String slug,
            Date startDate,
            Date endDate
    ){}
}
