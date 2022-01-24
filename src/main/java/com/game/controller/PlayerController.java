package com.game.controller;

import com.game.entity.Player;
import com.game.error.BadIdException;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public List<Player> getPlayersList(@RequestParam Map<String, String> requestParams) {
        return playerService.getPlayers(requestParams);
    }

    @GetMapping("/players/count")
    public Integer getPlayersCount(@RequestParam Map<String, String> requestParams) {
        return playerService.getPlayersCount(requestParams);
    }

    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable String id) {
        return playerService.getPlayerById(id);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable String id) {
        playerService.deletePlayer(id);
    }

    @PostMapping("/players")
    public Player createPlayer(@RequestBody Map<String, String> requestParams) {
        return playerService.savePlayer(requestParams);
    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable String id, @RequestBody Map<String, String> requestParams) {
        return playerService.updatePlayer(id, requestParams);
    }

}