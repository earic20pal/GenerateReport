/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.action;

import com.myapp.dao.TC_idDao;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ravi
 */
public class TcAction {

    public List getAllTc_id(String TC_id) {
        System.out.println("=================Get Message From Ajax===============");
        TC_idDao TC_iddao = new TC_idDao();
        List TC_ids = TC_iddao.getAllTC_id(TC_id);
        return TC_ids;
    }

    public List getAllModule() {
        TC_idDao tcdao = new TC_idDao();
        List list = tcdao.getAllModule();
        list.add(0, "Select Module");
        return list;
    }

    public List getModuleTC(String Module) {
        TC_idDao tcdao = new TC_idDao();
        List list = tcdao.getModuleTC(Module);
        list.add(0, "Select Test Case");
        return list;
    }

    public String getDetails(String TC_id) {
        TC_idDao TC_iddao = new TC_idDao();
        String sbr = TC_iddao.getDetails(TC_id);
        return sbr;
    }
    public String getDesc(String TC_id) {
        TC_idDao TC_iddao = new TC_idDao();
        String sbr = TC_iddao.getDesc(TC_id);
        return sbr;
    }
    public String getModuleDetails(String module) {
        TC_idDao TC_iddao = new TC_idDao();
        String sbr = TC_iddao.getModuleDetails(module);
        return sbr;
    }
    public String runModule(String module) {
    	TC_idDao TC_iddao = new TC_idDao();
    	String sbr = TC_iddao.runModule(module);
    	return sbr;
    }

}
