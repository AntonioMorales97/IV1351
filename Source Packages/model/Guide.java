package model;

import java.util.ArrayList;
import java.util.List;

import dto.GuideDTO;
import dto.LanguageDTO;
import dto.ShowExpertiesDTO;

public class Guide {
    private GuideDTO guideDTO;
    private List<LanguageDTO> languages;
    private List<ShowExpertiesDTO> showExperties; //utställningskompetens. kanske skapa object med id...
    
    public Guide (GuideDTO guideDTO) {
        this.guideDTO = guideDTO;
        this.languages = new ArrayList<LanguageDTO>();
        this.showExperties = new ArrayList<ShowExpertiesDTO>();
        //fill languages from database
        //fill showExperties from database
        addLanguage("spanska"); //remove
        addLanguage("svenska"); //remove
        addShow("The Opening");
    }
    
    
    
    public void addLanguage(String language) {
        LanguageDTO languageDTO = new LanguageDTO(language);
        languages.add(languageDTO);
    }
    
    public void addShow(String show) {
        ShowExpertiesDTO showExpertiesDTO = new ShowExpertiesDTO(show);
        showExperties.add(showExpertiesDTO);
    }
    
    public List<LanguageDTO> getLanguages(){
        return this.languages;
    }
    
    public List<ShowExpertiesDTO> getShowExperties(){
        return this.showExperties;
    }
    
    public GuideDTO getGuideDTO() {
        return this.guideDTO;
    }
}
