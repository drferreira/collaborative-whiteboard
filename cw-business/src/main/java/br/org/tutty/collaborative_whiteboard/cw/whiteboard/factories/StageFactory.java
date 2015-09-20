package br.org.tutty.collaborative_whiteboard.cw.whiteboard.factories;

import br.org.tutty.Equalizer;
import cw.entities.Stage;
import dtos.StagesMailable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by drferreira on 19/09/15.
 */
public class StageFactory {
	private Set<Stage> stages;

	public StageFactory(Set<Stage> stages) {
		this.stages = stages;
	}

	public List<StagesMailable> getStagesMailables() {
		List<StagesMailable> stagesMailables = new ArrayList<>();

		for (Stage stage : stages) {
			try{
				stagesMailables.add(builderStage(stage));
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		return stagesMailables;
	}

	public Set<Stage> getStages() {
		return stages;
	}

	public StagesMailable builderStage(Stage stage) throws NoSuchFieldException, IllegalAccessException {
		StagesMailable stageMailable;

		stageMailable = new StagesMailable();
		Equalizer.equalize(stage, stageMailable);

		return stageMailable;
	}
}
