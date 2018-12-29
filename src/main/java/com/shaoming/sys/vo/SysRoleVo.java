package com.shaoming.sys.vo;

import com.shaoming.sys.model.SysRole;
import lombok.Data;

/**
 * Created by ShaoMing on 2018/5/18
 */
@Data
public class SysRoleVo extends SysRole {
    private Boolean checked;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
