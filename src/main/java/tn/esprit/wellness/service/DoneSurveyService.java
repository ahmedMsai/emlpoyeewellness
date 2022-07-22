package tn.esprit.wellness.service;

import tn.esprit.wellness.entity.DoneSurvey;


import java.util.List;
import java.util.UUID;

public interface DoneSurveyService {

    DoneSurvey createDoneSurvey(DoneSurvey doneSurvey);
    DoneSurvey getDoneSurveyById(UUID id);
    List<DoneSurvey> getAllDoneSurvey();

}
