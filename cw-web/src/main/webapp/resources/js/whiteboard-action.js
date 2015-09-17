function action_task(action, element_event){
    var task_code = $(element_event).parent().siblings('.ticket_code').children().text();

    if(action == 'PLAY'){
        task_play([{name:'task_code', value:task_code}]);

    }else if(action == 'STOP'){
        task_stop([{name:'task_code', value:task_code}])

    }else if(action == 'NEXT'){
        task_next([{name:'task_code', value:task_code}]);

    }else if(action == 'PREV'){
        task_prev([{name:'task_code', value:task_code}])

    }else if(action == 'FINALIZE'){
        task_finalize([{name:'task_code', value:task_code}])

    }else if(action == 'LOAD_DETAIL'){
        var task_code = $(element_event).children('.ticket_code').children().text();
        load_task([{name:'task_code', value:task_code}])
    }
}
