function write_last_upadate(target, date) {
	target.empty();
	var span_date = $(document.createElement('span'));

	var date_object = new Date(date);
	var date_formated = (date_object.getDate() + " / " + (date_object.getMonth() + 1) + " / " + date_object.getFullYear() + "   " + date_object.getHours() + ":" + date_object.getMinutes());

	span_date.text(date_formated);
	target.append(span_date);
}

function write_all_stages(target, stages) {
	stages.sort(sortByProperty('position'));

	var stageWidth = calcSizeStage(stages.length, target);

	$.each(stages, function (key, value) {
		write_stage(target, value, stageWidth);
	});
}

function calcSizeStage(countStages, target) {
	var targetWidth = $(target).width();
	return targetWidth / countStages;
}

function write_stage(target, stage, width) {
	var stage_name = stage.name;

	var div_stage = $(document.createElement('div'));
	div_stage.attr('id', stage_name);
	div_stage.attr('class', 'stage');
	div_stage.css('width', width);

	write_stage_header(stage_name, div_stage);
	target.append(div_stage);
}

function write_stage_header(label, stage) {
	var header = $(document.createElement('div'));
	var headerLabel = document.createElement('span');

	headerLabel.textContent = label;
	header.addClass('stage_header');
	header.addClass('highlighted_font');

	header.append(headerLabel);
	stage.append(header);
}

function write_tasks(whiteboardJson) {
	var stages = whiteboardJson['stages'];

	if (stages.length != 0) {
		$.each(stages, function (key, stage) {
			var stories = stage.stories;

			//stories.sort(sortByProperty('storyPoints'));

			if (stories.length != 0) {
				$.each(stories, function (key, story) {
					var tasks = story.tasks;

					//tasks.sort(sortByProperty('code'));

					if (tasks.length != 0) {
						$.each(tasks, function (key, task) {
							write_task(stage, task)
						});
					}
				});
			}
		});
	}
}

function write_task(stage_target, taskJson) {
	var task = $(document.createElement('div'));
	task.attr('id', taskJson.code);
	task.addClass('task_ticket');

	task.append(write_task_code(taskJson.code));
	task.append(write_image_user(taskJson.taskStatus,taskJson.profilePicture));
	task.append(write_task_status(taskJson.taskStatus, task));
	task.append(write_task_subject(taskJson.subject));
	task.append(write_task_estimated(taskJson.estimatedTime))
		task.append(write_task_commands());

	task.dblclick(function () {
		action_task('LOAD_DETAIL', task);
	});

	$('#' + stage_target.name).append(task);
}

function write_task_status(task_status, ticket){
	ticket.addClass('ticket_status');
	ticket.addClass('ticket_status_'+task_status.value);
}

function write_image_user(task_status, task_user_picture) {
	if (task_status.value == 'BUSY') {
		var image_div = $(document.createElement('div'));
		image_div.addClass('image_user');

		var img = $(document.createElement('img'));
		img.attr('src', 'data:image/jpeg;base64,'+task_user_picture);

		image_div.append(img);
	}

	return image_div;
}

function write_task_code(code) {
	var task_div = $(document.createElement('div'));
	var task_code = $(document.createElement('span'));

	task_div.attr('class', 'ticket_code');
	task_code.text(code);

	task_div.append(task_code);

	return task_div;
}

function write_task_estimated(estimated) {
	var task_estimated_time_div = $(document.createElement('div'));
	var estimated_time = $(document.createElement('span'));

	task_estimated_time_div.attr('class', 'ticket_estimated');

	var formated_estimated_time = moment(estimated).format('hh:mm');
	estimated_time.text(formated_estimated_time);

	task_estimated_time_div.append(estimated_time);

	return task_estimated_time_div;
}

function write_task_commands() {
	var task_commands_div = $(document.createElement('div'));
	task_commands_div.attr('class', 'ticket_commands_area');

	var backwards = $(document.createElement('img'));
	backwards.attr('class', 'backwards_command');
	backwards.attr('onclick', "action_task('PREV', this);");
	backwards.attr('src', '../../images/icons/previous.png');
	backwards.addClass('task_command');

	var stop = $(document.createElement('img'));
	stop.attr('class', 'stop_command');
	stop.attr('onclick', "action_task('STOP', this);");
	stop.attr('src', '../../images/icons/stop.png');
	stop.addClass('task_command');

	var play = $(document.createElement('img'));
	play.attr('class', 'play_command');
	play.attr('onclick', "action_task('PLAY', this);");
	play.attr('src', '../../images/icons/play.png');
	play.addClass('task_command');

	var forward = $(document.createElement('img'));
	forward.attr('class', 'forward_command');
	forward.attr('onclick', "action_task('NEXT', this);");
	forward.attr('src', '../../images/icons/next.png');
	forward.addClass('task_command');

	var finalize = $(document.createElement('img'));
	finalize.attr('class', 'finalize_command');
	finalize.attr('onclick', "action_task('FINALIZE', this);");
	finalize.attr('src', '../../images/icons/finalize.png');
	finalize.addClass('task_command');

	task_commands_div.append(backwards);
	task_commands_div.append(stop);
	task_commands_div.append(play);
	task_commands_div.append(forward);
	task_commands_div.append(finalize);

	return task_commands_div;
}

function write_task_subject(subject_value) {
	var task_subject_div = $(document.createElement('div'));
	var subject = $(document.createElement('p'));

	task_subject_div.attr('class', 'ticket_subject');

	subject.text(subject_value);

	task_subject_div.append(subject);

	return task_subject_div;
}

function write_whiteboard_message(whiteboard, messageText) {
	var messageDiv = $(document.createElement('div'));
	var message = document.createElement('span');

	message.textContent = messageText;

	messageDiv.attr('class', 'message');

	$(messageDiv).append(message);
	$(whiteboard).append(messageDiv);
	center_whiteboard();
	remove_motion(whiteboard);
}
