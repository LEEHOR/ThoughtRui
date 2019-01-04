package com.coahr.thoughtrui.mvp.view.SubjectList.node;


import com.coahr.thoughtrui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HQOCSHheqing on 2016/8/2.
 *
 * @description 节点帮助类
 */
public class NodeHelper {

    /**
     * 传入所有的要展示的节点数据
     * @param baseNodeList  返回值是所有的根节点
     * @return
     */
    public static List<BaseNode> sortNodes(List<BaseNode> baseNodeList){

        List<BaseNode> rootBaseNodes = new ArrayList<>();
        int size = baseNodeList.size();
        BaseNode m;
        BaseNode n;
        //两个for循环整理出所有数据之间的父子关系，最后会构造出一个森林（就是可能有多棵树）
        for (int i = 0;i < size;i++){
            m = baseNodeList.get(i);
            for (int j = i+1;j < size;j++){
                n = baseNodeList.get(j);
                if (m.parent(n)){
                    m.get_childrenList().add(n);
                    n.set_parent(m);
                }else if (m.child(n)){
                    n.get_childrenList().add(m);
                    m.set_parent(n);
                }
            }
        }
        //找出所有的树根，同事设置相应的图标（后面你会发现其实这里的主要
        // 作用是设置叶节点和非叶节点的图标）
        for (int i = 0;i < size;i++){
            m = baseNodeList.get(i);
            if (m.isRoot()){
                rootBaseNodes.add(m);
            }
            setNodeIcon(m);
        }
        baseNodeList.clear();//此时所有的关系已经整理完成了
        // ，森林构造完成，可以清空之前的数据，释放内存，提高性能
        // ，如果之前的数据还有用的话就不清空
        baseNodeList = rootBaseNodes;//返回所有的根节点
        rootBaseNodes = null;
        return baseNodeList;
    }

    /**
     * 设置图标
     * @param baseNode
     */
    private static void setNodeIcon(BaseNode baseNode){
        if (!baseNode.isLeaf()){
            if (baseNode.isExpand()){
                baseNode.set_icon(R.mipmap.collapse);
            }else{
                baseNode.set_icon(R.mipmap.expand);
            }
        }else{
            baseNode.set_icon(-1);
        }
    }
}
