package com.hmatalonga.greenhub.response;


import com.hmatalonga.greenhub.models.ui.Task;

import java.util.List;

public class AppListData {

    public List<Task> getList() {
        return list;
    }

    public void setList(List<Task> list) {
        this.list = list;
    }

    private List<Task> list;


}
