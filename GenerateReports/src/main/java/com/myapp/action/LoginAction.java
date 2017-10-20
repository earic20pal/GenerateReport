/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.action;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author ravi
 */
public class LoginAction extends DispatchAction {

 
    public ActionForward login(ActionMapping mapping,ActionForm form,
		HttpServletRequest request,HttpServletResponse response)
        throws Exception {

        String uname=request.getParameter("Username");
        String pwd=request.getParameter("password");
        if(uname.equals("adm"))
        {
            if(pwd.equals("t0n1ght"))
            {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                return mapping.findForward("success");
            }
            else
                return mapping.findForward("error");
        }
		

		return mapping.findForward("error");
	}
        
    

    
   
}
