package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SiteModel
{
    @JsonProperty("entry")
    SiteModel model;

    private String role;
    private String visibility;
    private String guid;
    private String id;
    private String preset;
    private String title;

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getVisibility()
    {
        return visibility;
    }

    public void setVisibility(String visibility)
    {
        this.visibility = visibility;
    }

    public String getGuid()
    {
        return guid;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPreset()
    {
        return preset;
    }

    public void setPreset(String preset)
    {
        this.preset = preset;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public SiteModel onModel() {
        return this.model;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SiteModel siteModel = (SiteModel) o;
        return Objects.equals(guid, siteModel.guid) && Objects.equals(id, siteModel.id);
    }

    @Override public int hashCode()
    {
        return Objects.hash(guid, id);
    }
}
