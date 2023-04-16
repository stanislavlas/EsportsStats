package com.esportsstats.api.leagueoflegends;

import com.esportsstats.model.leagueoflegends.LeaguesModel;
import lombok.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaguesAPI implements Serializable {
    public List<League> leagues = new LinkedList<>();

    public LeaguesAPI (LeaguesModel leaguesModel) {
        for(var league: leaguesModel.data.leagues()){
            leagues.add(new League(
                    league.id(),
                    league.slug(),
                    league.name(),
                    league.region(),
                    league.image()
            ));
        }
    }

    public record League (
        String id,
        String slug,
        String name,
        String region,
        String image
    ) {}
}
