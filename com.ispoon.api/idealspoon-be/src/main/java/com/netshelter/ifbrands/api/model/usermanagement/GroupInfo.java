package com.netshelter.ifbrands.api.model.usermanagement;

/**
 * User: Dmitriy T
 * Date: 2/5/13
 */
public class GroupInfo
{
    private String id;
    private String name;

    public GroupInfo( String id, String name ) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
}
