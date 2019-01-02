package com.coahr.thoughtrui.mvp.view.SubjectList;

import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.QuestionList;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.ValueBean;
import com.coahr.thoughtrui.mvp.view.SubjectList.node.Node;

import java.util.List;

/**
 * Created by Leehor
 * on 2019/1/2
 * on 16:06
 */
public class ThressTest extends Node<String> {


    /**
     * id : f4cc268df3f547e4b6b9f356df3b5526
     * name : 档案管理
     * quesList : []
     * value : [{"id":"f808583f51d94811b67f1bef6470afb4","name":"档案建立及准确性","quesList":[{"id":"3e7a5d8c9db34f40b11aa8aa144b126f","title":"建立准确完整的电子版和纸质客户档案，保证一车一档。"},{"id":"94eba319c30a4c868a97fdaafb419012","title":"建立准确完整的电子版和纸质客户档案，保证一车一档。"},{"answer":"否","id":"8593d8a049bc4124af32d44490716406","picture":"25_1.jpg;25_2.jpg;25_3.jpg;25_4.jpg;25_5.jpg;25_6.jpg","title":"建立准确完整的电子版和纸质客户档案，保证一车一档。"},{"answer":"否","id":"fbad8129918341148dee5ad22680e0ec","picture":"47_1.jpg;47_2.jpg;47_3.jpg;47_4.jpg;47_5.jpg","title":"建立准确完整的电子版和纸质客户档案，保证一车一档。"}]}]
     */
    private String id;  //部门id
    private String parentId; //父节点id
    private String name;
    @Override
    public String get_id() {
        return id;
    }

    @Override
    public String get_parentId() {
        return parentId;
    }

    @Override
    public String get_label() {
        return name;
    }

    @Override
    public boolean parent(Node dest) {
        if (id.equals(dest.get_parentId())){
            return true;
        }
        return false;
    }

    @Override
    public boolean child(Node dest) {
        if (parentId.equals(String.valueOf(dest.get_id()))){
            return true;
        };
        return false;
    }


    public String getParentId() {
        return parentId;
    }


    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ThressTest() {
    }

    public ThressTest(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;

    }
}
