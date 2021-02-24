package com.friendlyitsolution.meet.irvapp.recy;

/**
 * Created by Meet on 16-10-2017.
 */

public class fines_model
{
    String date,amt,rule,key,status;

    public fines_model(String key,String rule,String amt,String date,String status)
    {
        this.date=date;
        this.status=status;
        this.key=key;
        this.rule=rule;
        this.amt=amt;
    }

}
