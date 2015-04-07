package br.org.tutty.collaborative_whiteboard;

import cw.entities.Project;
import cw.entities.ProjectArea;

import java.util.List;

/**
 * Created by drferreira on 07/04/15.
 */
public interface ProjectAreaDao extends Dao{
    List<ProjectArea> filterProjectAreas(Project project, String queryName);
}
