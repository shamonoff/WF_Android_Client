package com.example.jbulavincev.mobileappexperiments;

/**
 * Created by jbulavincev on 05.01.2015.
 */
public class WorkorderData
{
    public String date;
    public String worker;
    public String woid;
    public String enddate;
    public String latitude;
    public String address;
    public String state;
    public  String type;
    public  String startdate;
    public String uuid;
    public String status;
    public String customer;
    public String longitude;
    public String crew;

    public WorkorderData(String date, String worker, String woid)
    {
        this.date=date;
        this.woid=woid;
        this.worker=worker;
    }

    public WorkorderData()
    {

    }

    public void setDate (String date)
    {
        this.date=date;
    }

    public void setWorker (String worker)
    {
        this.worker=worker;
    }

    public void setWOId (String woid)
    {
        this.woid=woid;
    }

    public void setEnddate (String enddate)
    {
        this.enddate=enddate;
    }

    public void setLatitude (String latitude)
    {
        this.latitude=latitude;
    }

    public void setAddress (String address)
    {
        this.address=address;
    }

    public void setState (String state)
    {
        this.state=state;
    }

    public void setType (String type)
    {
        this.type=type;
    }

    public void setStartdate (String startdate)
    {
        this.startdate=startdate;
    }

    public void setUuid (String uuid)
    {
        this.uuid=uuid;
    }

    public void setStatus (String status)
    {
        this.status=status;
    }

    public void setCustomer (String customer)
    {
        this.customer=customer;
    }

    public void setLongitude (String longitude)
    {
        this.longitude=longitude;
    }

    public void setCrew (String crew)
    {
        this.crew=crew;
    }
}
