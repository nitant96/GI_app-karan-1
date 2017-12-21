package gov.cipam.gi.model;

import java.io.Serializable;

/**
 * Created by Deepak on 11/18/2017.
 */

public class Categories implements Serializable {
    private String name,dpurl;

    public Categories(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDpurl() {
        return dpurl;
    }

    public void setDpurl(String dpurl) {
        this.dpurl = dpurl;
    }
}
