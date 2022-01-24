package com.game.repository;

import com.game.entity.Player;
import com.game.entity.PlayerDTO;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.beans.support.PagedListHolder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class RepositoryCustomImpl implements RepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer countAllPlayers(PlayerDTO playerDTO) {
        return parserPlayers(playerDTO).size();
    }

    @Override
    public List<Player> findAllPlayers(PlayerDTO playerDTO) {

        List<Player> result = parserPlayers(playerDTO);

        if (result.size() != 0) {
            sortPlayers(result, playerDTO.getOrder());
            PagedListHolder page = new PagedListHolder(result);
            page.setPage(playerDTO.getPageNumber());
            page.setPageSize(playerDTO.getPageSize());
            return page.getPageList();
        }
        return result;
    }

    private void sortPlayers(List<Player> result, String kindOfSort) {
        switch (kindOfSort) {
            case "id":
                result.sort(Comparator.comparing(Player::getId));
                break;
            case "name":
                result.sort(Comparator.comparing(Player::getName));
                break;
            case "experience":
                result.sort(Comparator.comparing(Player::getExperience));
                break;
            case "birthday":
                result.sort(Comparator.comparing(Player::getBirthday));
                break;
            case "level":
                result.sort(Comparator.comparing(Player::getLevel));
                break;
        }
    }

    private List<Player> parserPlayers(PlayerDTO playerDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();

        Root<Player> player = query.from(Player.class);

        List<Predicate> predicates = new ArrayList<>();

        if (playerDTO.getName() != null) {
            Path<String> namePath = player.get("name");
            predicates.add(cb.like(namePath, "%" + playerDTO.getName() + "%"));
        }

        if (playerDTO.getTitle() != null) {
            Path<String> titlePath = player.get("title");
            predicates.add(cb.like(titlePath, "%" + playerDTO.getTitle() + "%"));
        }

        if (playerDTO.getAfter() != null) {
            Path<Date> afterPath = player.get("birthday");
            Date dateStart = new Date(playerDTO.getAfter());
            predicates.add(cb.greaterThan(afterPath, dateStart));
        }

        if (playerDTO.getBefore() != null) {
            Path<Date> beforePath = player.get("birthday");
            Date dateEnd = new Date(playerDTO.getBefore());
            predicates.add(cb.lessThan(beforePath, dateEnd));
        }


        if (playerDTO.getRace() != null) {
            Path<Race> racePath = player.get("race");
            predicates.add(cb.equal(racePath, playerDTO.getRace()));
        }

        if (playerDTO.getProfession() != null) {
            Path<Profession> professionPath = player.get("profession");
            predicates.add(cb.equal(professionPath, playerDTO.getProfession()));
        }

        if (playerDTO.getBanned() != null) {
            Path<Boolean> bannedPath = player.get("banned");
            predicates.add(cb.equal(bannedPath, playerDTO.getBanned()));
        }

        if (playerDTO.getMinExperience() != null) {
            Path<Integer> minExperiencePath = player.get("experience");
            predicates.add(cb.greaterThanOrEqualTo(minExperiencePath, playerDTO.getMinExperience()));
        }

        if (playerDTO.getMaxExperience() != null) {
            Path<Integer> maxExperiencePath = player.get("experience");
            predicates.add(cb.lessThanOrEqualTo(maxExperiencePath, playerDTO.getMaxExperience()));
        }

        if (playerDTO.getMinLevel()!= null) {
            Path<Integer> minLevelPath = player.get("level");
            predicates.add(cb.greaterThanOrEqualTo(minLevelPath, playerDTO.getMinLevel()));
        }

        if (playerDTO.getMaxLevel() != null) {
            Path<Integer> maxLevelPath = player.get("level");
            predicates.add(cb.lessThanOrEqualTo(maxLevelPath, playerDTO.getMaxLevel()));
        }

        if (predicates.size() != 0) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        return entityManager.createQuery(query.select(player)).getResultList();
    }
}