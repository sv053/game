package com.game.entity;

import com.game.controller.PlayerOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.Map;

public class PlayerDTO {

    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long after;
    private Long before;
    private Boolean banned;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minLevel;
    private Integer maxLevel;
    private String order;
    private Integer pageNumber;
    private Integer pageSize;

    public PlayerDTO(Map<String, String> params) {

        this.pageNumber = Integer.parseInt(params.getOrDefault("pageNumber", "0"));
        this.pageSize = Integer.parseInt(params.getOrDefault("pageSize", "3"));
        this.order = params.getOrDefault("order", "ID").toLowerCase();

        this.name = params.get("name");
        this.title = params.get("title");

        if (params.get("race") != null)
            this.race = Race.valueOf(params.get("race"));
        if (params.get("profession") != null)
            this.profession = Profession.valueOf(params.get("profession"));

        if (params.get("after") != null)
            this.after = Long.parseLong(params.get("after"));
        if (params.get("before") != null)
            this.before = Long.parseLong(params.get("before"));

        if (params.get("banned") != null)
            this.banned = Boolean.valueOf(params.get("banned"));

        if (params.get("minExperience") != null)
            this.minExperience = Integer.parseInt(params.get("minExperience"));
        if (params.get("maxExperience") != null)
            this.maxExperience = Integer.parseInt(params.get("maxExperience"));

        if (params.get("minLevel") != null)
            this.minLevel = Integer.parseInt(params.get("minLevel"));
        if (params.get("maxLevel") != null)
            this.maxLevel = Integer.parseInt(params.get("maxLevel"));
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Race getRace() {
        return race;
    }

    public Profession getProfession() {
        return profession;
    }

    public Long getAfter() {
        return after;
    }

    public Long getBefore() {
        return before;
    }

    public Boolean getBanned() {
        return banned;
    }

    public Integer getMinExperience() {
        return minExperience;
    }

    public Integer getMaxExperience() {
        return maxExperience;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public String getOrder() {
        return order;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
