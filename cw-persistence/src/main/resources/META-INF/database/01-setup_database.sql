-- View: extraction_iteration
-- DROP VIEW extraction_iteration;
CREATE OR REPLACE VIEW extraction_iteration AS SELECT iteration.name, to_char(iteration.initdate, 'DD/MM/YY'::text) AS inicio, to_char(iteration.enddate, 'DD/MM/YY'::text) AS fim, date_part('days'::text, iteration.enddate - iteration.initdate) AS dias, ( SELECT sum(story.story_points) AS sum FROM story WHERE story.iteration_id = iteration.id) AS pontos, ( SELECT count(story.id) AS count FROM story WHERE story.iteration_id = iteration.id) AS estorias, ( SELECT count(task.id) AS count FROM task JOIN story ON story.id = task.story_id AND story.iteration_id = iteration.id) AS tarefas, ( SELECT count(DISTINCT "user".firstname) AS count FROM task_status status JOIN "user" ON status.user_id = "user".id JOIN task ON task.id = status.task_id JOIN story ON story.id = task.story_id WHERE status.taskstatus::text = 'BUSY'::text AND story.iteration_id = iteration.id) AS desenvolvedores FROM iteration;ALTER TABLE extraction_iteration OWNER TO postgres;

-- View: extraction_task
-- DROP VIEW extraction_task;
CREATE OR REPLACE VIEW extraction_task AS SELECT story.code AS story_code, to_char(story.creationdate, 'DD/MM/YY HH24:MI'::text) AS story_creation, story.subject AS story_subject, story.description AS story_description, story.priority AS story_priority, story.story_points,iteration.name AS iteration_name, to_char(iteration.initdate, 'DD/MM/YY'::text) AS iteration_init, to_char(iteration.enddate, 'DD/MM/YY'::text) AS iteration_end, task.code AS task_code, task.description AS task_description, task.subject AS task_subject, to_char(task.estimatedtime, 'HH24:MI'::text) AS task_estimated_time, task_status.taskstatus AS task_status FROM story JOIN task ON task.story_id = story.id JOIN task_status ON task_status.task_id = task.id JOIN iteration ON iteration.id = story.iteration_id ORDER BY story.code, task.code; ALTER TABLE extraction_task OWNER TO postgres;

-- View: extraction_user
-- DROP VIEW extraction_user;
CREATE OR REPLACE VIEW extraction_user AS SELECT "user".firstname AS nome, "user".email, ( SELECT count(*) AS count FROM task_status status WHERE status.user_id = "user".id AND status.taskstatus::text = 'BUSY'::text) AS tarefas, ( SELECT round(avg(result.count), 2) AS round FROM ( SELECT count(*) AS count FROM task_status status WHERE status.taskstatus::text = 'BUSY'::text GROUP BY status.user_id) result) AS media FROM "user"; ALTER TABLE extraction_user OWNER TO postgres;

-- View: extraction_user
-- DROP VIEW extraction_user;
CREATE OR REPLACE VIEW extraction_user AS SELECT "user".firstname AS nome, "user".email, ( SELECT count(*) AS count FROM task_status status WHERE status.user_id = "user".id AND status.taskstatus::text = 'BUSY'::text) AS tarefas, ( SELECT round(avg(result.count), 2) AS round FROM ( SELECT count(*) AS count FROM task_status status WHERE status.taskstatus::text = 'BUSY'::text GROUP BY status.user_id) result) AS media FROM "user"; ALTER TABLE extraction_user OWNER TO postgres;

