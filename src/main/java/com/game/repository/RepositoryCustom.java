package com.game.repository;

import com.game.entity.Player;
import com.game.entity.PlayerDTO;
import com.game.entity.Race;

import java.util.List;

public interface RepositoryCustom {
    List<Player> findAllPlayers(PlayerDTO playerDTO);
    Integer countAllPlayers(PlayerDTO playerDTO);
}
