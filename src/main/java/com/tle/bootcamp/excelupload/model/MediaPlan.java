package com.tle.bootcamp.excelupload.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MediaPlan {
    private String day;
    private String date;
    private String station;
    private String title;
    private Double startTime;
    private Double endTime;
    private Double duration;
    private Double uOne;
    private String comment;
    private String featCode;
    private String rateCode;
    private Double rateCardCost;
    private Double cost;
    private Double breakViewers;
    private Double breakCPT;
    private Double breakTVR;
    private Double breakCPP;
    private Double planTVR;
}
