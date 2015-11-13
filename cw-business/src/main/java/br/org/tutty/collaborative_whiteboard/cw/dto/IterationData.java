package br.org.tutty.collaborative_whiteboard.cw.dto;

import br.org.tutty.Equalization;

import java.util.Date;
import java.util.List;

public class IterationData extends IterationBasicData{
    @Equalization(name = "init_date")
    private Date init;

    @Equalization(name = "end_date")
    private Date end;

    private List<StoryBasicData> storyBasicDatas;

    public void addStory(StoryBasicData storyBasicData){
        storyBasicDatas.add(storyBasicData);
    }

}
