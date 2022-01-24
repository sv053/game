package com.game.service;

import com.game.entity.Player;
import com.game.entity.PlayerDTO;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.error.BadIdException;
import com.game.error.PlayerNotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers(Map<String, String> params) {
        PlayerDTO playerDTO = new PlayerDTO(params);
        return playerRepository.findAllPlayers(playerDTO);
    }

    public Integer getPlayersCount(Map<String, String> params) {
        PlayerDTO playerDTO = new PlayerDTO(params);
        return playerRepository.countAllPlayers(playerDTO);
    }

    //todo
    public void deletePlayer(String id) {
        Long idLong = 0L;
        try {
            idLong = Long.parseLong(id);
        }
        catch (NumberFormatException exception) {
            throw new BadIdException(String.format("Bad id to delete = [%s]", id));
        }
        if (idLong <= 0) {
            throw new BadIdException(String.format("Bad id to delete = [%s]", id));
        }
        Optional<Player> playerForDelete = playerRepository.findById(idLong);
        if (!playerForDelete.isPresent()) {
            throw new PlayerNotFoundException(String.format("Player with ID=[%d] doesn't exist", idLong));
        }
        playerRepository.deleteById(idLong);
    }


    //todo
    public Player getPlayerById(String id) {
        Long idLong = 0L;
        try {
            idLong = Long.parseLong(id);
        }
        catch (NumberFormatException exception) {
            throw new BadIdException(String.format("Bad id to find = [%s]", id));
        }
        if (idLong <= 0) {
            throw new BadIdException(String.format("Bad id to find = [%s]", id));
        }
        Optional<Player> playerById = playerRepository.findById(idLong);
        if (!playerById.isPresent()) {
            throw new PlayerNotFoundException(String.format("Player with ID=[%d] doesn't exist", idLong));
        }
        return playerById.get();
    }

    public Player savePlayer(Map<String, String> params) {

        Player player = new Player();

        if (params.get("name") == null || params.get("title") == null)
            throw new BadIdException("Bad params");
        if (params.get("name").isEmpty() || params.get("title").isEmpty())
            throw new BadIdException("Bad params");
        if (params.get("name").length() > 12 || params.get("title").length() > 30)
            throw new BadIdException("Bad params");

        if (params.get("experience") == null)
            throw new BadIdException("Bad params");
        player.setExperience(Integer.parseInt(params.get("experience")));
        if (player.getExperience() < 0 || player.getExperience() > 10_000_000)
            throw new BadIdException("Bad params");

        if (params.get("birthday") == null)
            throw new BadIdException("Bad params");
        if (Long.parseLong(params.get("birthday")) < 0)
            throw new BadIdException("Bad params");
        player.setBirthday(new Date(Long.parseLong(params.get("birthday"))));
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            Date date2000 = formatter.parse("1-Jun-2000");
            Date date3000 = formatter.parse("1-Jun-3000");
            if (player.getBirthday().before(date2000) || player.getBirthday().after(date3000))
                throw new BadIdException("Bad params");
        }
        catch (ParseException exception) {}

        player.setName(params.get("name"));
        player.setTitle(params.get("title"));
        player.setRace(Race.valueOf(params.get("race")));
        player.setProfession(Profession.valueOf(params.get("profession")));

        if (params.get("banned") != null) {
            player.setBanned(Boolean.parseBoolean(params.get("banned")));
        }
        else {
            player.setBanned(false);
        }

        Integer level = (int)((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        Integer untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);

        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(String id, Map<String, String> params) {
        Long idLong = 0L;
        try {
            idLong = Long.parseLong(id);
        }
        catch (NumberFormatException exception) {
            throw new BadIdException(String.format("Bad id = [%s]", id));
        }
        if (idLong <= 0) {
            throw new BadIdException(String.format("Bad id = [%s]", id));
        }
        Optional<Player> playerById = playerRepository.findById(idLong);
        if (!playerById.isPresent()) {
            throw new PlayerNotFoundException(String.format("Player not found = [%s]", id));
        }
        if (params.isEmpty())
            return playerById.get();
        Player player = playerById.get();
        if (params.get("name") != null) {
            if (params.get("name").isEmpty() || params.get("name").length() > 12)
                throw new BadIdException(String.format("Bad name = [%s]", params.get("name")));
            player.setName(params.get("name"));
        }
        if (params.get("title") != null) {
            if (params.get("title").isEmpty() || params.get("title").length() > 30)
                throw new BadIdException(String.format("Bad title = [%s]", params.get("title")));
            player.setTitle(params.get("title"));
        }
        if (params.get("race") != null)
            player.setRace(Race.valueOf(params.get("race")));
        if (params.get("profession") != null)
            player.setProfession(Profession.valueOf(params.get("profession")));
        if (params.get("birthday") != null) {
            if (Long.parseLong(params.get("birthday")) < 0)
                throw new BadIdException(String.format("Bad birthday = [%s]", params.get("birthday")));
            player.setBirthday(new Date(Long.parseLong(params.get("birthday"))));
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                Date date2000 = formatter.parse("1-Jun-2000");
                Date date3000 = formatter.parse("1-Jun-3000");
                if (player.getBirthday().before(date2000) || player.getBirthday().after(date3000))
                    throw new BadIdException(String.format("Bad birthday = [%s]", params.get("birthday")));
            }
            catch (ParseException exception) {}
        }
        if (params.get("banned") != null)
            player.setBanned(Boolean.parseBoolean(params.get("banned")));
        if (params.get("experience") != null) {
            Integer experience = Integer.parseInt(params.get("experience"));
            if (experience < 0 || experience > 10_000_000)
                throw new BadIdException(String.format("Bad experience = [%s]", params.get("experience")));
            player.setExperience(experience);
            Integer level = (int)((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
            Integer untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
            player.setLevel(level);
            player.setUntilNextLevel(untilNextLevel);
        }
        playerRepository.save(player);
        return player;
    }
}