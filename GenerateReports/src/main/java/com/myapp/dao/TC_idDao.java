/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.dao;

import com.builds.generics.RunningXML;
import com.pojo.TcDesc;
import com.pojo.TcStatus;
import com.util.HibernateUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ravi
 */
public class TC_idDao {

    public List getAllTC_id(String TC_id) {
        SessionFactory factory;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Criteria crit = session.createCriteria(TcDesc.class);
        crit.add(Restrictions.like("tcId", "%" + TC_id + "%"));
        List<TcDesc> employees = crit.list();
        List emplist = new LinkedList();
        for (TcDesc obj : employees) {
            String fullname = obj.getTcId();
            emplist.add(fullname);
        }
        return emplist;
    }

    public String getDetails(String tcid) {
        //no
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        List<String> status = new LinkedList<String>();
        Map time = new HashMap();
        List<Date> date = new LinkedList<Date>();
        StringBuilder sbr = new StringBuilder();
        sbr.append("<table border=\"1\" style=\"width: 400px; height: 200px\"><tr>");
        try {

            SQLQuery query = session.createSQLQuery("SELECT * FROM TC_STATUS WHERE TC_ID='" + tcid + "'").addEntity(TcStatus.class);
            List<TcStatus> list = query.list();
            int i = 0;
            for (TcStatus obj : list) {
                System.out.println(obj.getStatus());
                status.add(obj.getStatus());
                date.add(obj.getDate());
                time.put(i, obj.getTime());
                i++;
            }
            sbr.append("<td></td>");
            i = 0;
            for (Date d : date) {
                sbr.append("<td style=\"background-color:Sienna;\"><center>" + String.valueOf(d) + " " + time.get(i) + "</center></td>");
                i++;
            }

            sbr.append("</tr><tr><td>" + tcid + "</td>");
            for (String str : status) {
                str.trim();
                if (!"".equals(str)) {

                    if (str.equalsIgnoreCase("PASS")) {
                        sbr.append("<td style=\"background-color:green;\">" + str + "</td>");
                    } else if ("FAIL".equalsIgnoreCase(str)) {
                        sbr.append("<td style=\"background-color:red;\">" + str + "</td>");
                    } else if ("SKIP".equalsIgnoreCase(str)) {
                        sbr.append("<td style=\"background-color:yellow;\">" + str + "</td>");
                    } else {
                        sbr.append("<td style=\"background-color:grey;\">" + str + "</td>");
                    }

                } else {
                    sbr.append("<td> Result Not Found regarding /<td>");
                }
            }
            sbr.append("</tr></table>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.clear();
        }
        return sbr.toString();

    }

    public List getAllModule() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        List list = null;
        try {
            SQLQuery query = session.createSQLQuery("SELECT DISTINCT MODULE FROM TC_STATUS");
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.clear();
        }
        System.out.println(list);
        return list;
    }

    public List getModuleTC(String Module) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        List list = null;
        try {
            SQLQuery query = session.createSQLQuery("SELECT DISTINCT TC_ID FROM TC_STATUS WHERE MODULE='" + Module + "'");

            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.clear();
        }
        System.out.println(list);
        return list;
    }

    public String getModuleDetails(String module) {

        //no
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        List<String> tcids = new LinkedList<String>();
        StringBuilder sbr = new StringBuilder();
        int once = 0;
        sbr.append("<table border=\"1\" style=\"width: 400px; height: 200px\"><tr>");
        try {
            SQLQuery query = session.createSQLQuery("Select * from TC_STATUS WHERE MODULE='" + module + "' GROUP BY TC_ID asc").addEntity(TcStatus.class);
            List<TcStatus> TClist = query.list();
            int[] countpass = null;
            int[] countfail = null;
            int[] countskip = null;
            for (TcStatus stobj : TClist) {
                tcids.add(stobj.getTcDesc().getTcId());
                List<String> status = new LinkedList<String>();
                Map time = new HashMap();
                List<Date> date = new LinkedList<Date>();
                query = session.createSQLQuery("SELECT * FROM TC_STATUS WHERE TC_ID='" + stobj.getTcDesc().getTcId() + "' AND MODULE='"+module+"'").addEntity(TcStatus.class);
                List<TcStatus> list = query.list();
                int i = 0;
                
                for (TcStatus obj : list) {
                    System.out.println(obj.getStatus());
                    status.add(obj.getStatus());
                    date.add(obj.getDate());
                    time.put(i, obj.getTime());
                    i++;

                }
                if (once == 0) {

                    sbr.append("<td></td>");
                    i = 0;
                    for (Date d : date) {
                        sbr.append("<td style=\"background-color:Sienna;\"><center>" + String.valueOf(d) + " " + time.get(i) + "</center></td>");
                        i++;
                    }
                    countpass=new int[i];
                    countfail=new int[i];
                    countskip=new int[i];
                    once++;
                }
                
               int c=0;
                sbr.append("</tr><tr><td style=\"background-color:LightSteelBlue;font-family: Arial, Helvetica, sans-serif;\"> <center>" + stobj.getTcDesc().getTcId() + "</center></td>");
                for (String str : status) {
                    str.trim();
                    if (!"".equals(str)) {

                        if (str.equalsIgnoreCase("PASS")) {
                            countpass[c]++;
                            sbr.append("<td style=\"background-color:OliveDrab;\">" + str + "</td>");
                        } else if ("FAIL".equalsIgnoreCase(str)) {
                            countfail[c]++;
                            sbr.append("<td style=\"background-color:LightCoral;\">" + str + "</td>");
                        } else if ("SKIP".equalsIgnoreCase(str)) {
                            countskip[c]++;
                            sbr.append("<td style=\"background-color:PapayaWhip;\">" + str + "</td>");
                        } else {
                            sbr.append("<td style=\"background-color:grey;\">" + str + "</td>");
                        }
                        c++;

                    } else {
                        sbr.append("<td> Result Not Found regarding /<td>");
                    }
                }
            }
            
            
            sbr.append("</tr><tr><td>Pass</td>");
            int val=0;
            for(int i:countpass)
            {
               sbr.append("<td>"+String.valueOf(countpass[val])+"</td>"); 
               val++;
            }
            sbr.append("</tr><tr><td>Fail</td>");
            val=0;
            for(int i:countpass)
            {
               sbr.append("<td>"+String.valueOf(countfail[val])+"</td>"); 
               val++;
            }
            sbr.append("</tr><tr><td>Skip</td>");
            val=0;
            for(int i:countpass)
            {
               sbr.append("<td>"+String.valueOf(countskip[val])+"</td>"); 
               val++;
            }
            sbr.append("</tr></table>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.clear();
        }
        return sbr.toString();

    }

    public String getDesc(String TC_id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        String str = null;
        try {
            Criteria crit = session.createCriteria(TcDesc.class);
            crit.add(Restrictions.eq("tcId", TC_id));
            List<TcDesc> result = crit.list();
            System.out.println(result.get(0).getDescription());
            str = result.get(0).getDescription();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

	public String runModule(String module) {
		RunningXML obj= new RunningXML();
		obj.Runfile();
		return null;
	}

}
