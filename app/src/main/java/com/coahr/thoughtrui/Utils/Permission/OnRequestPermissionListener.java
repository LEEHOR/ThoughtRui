package com.coahr.thoughtrui.Utils.Permission;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/10/26
 * on 11:41
 */
public interface OnRequestPermissionListener {
    void PermissionSuccess(List<String> permissions);
    void PermissionFail(List<String> permissions);
    void PermissionHave();
}
