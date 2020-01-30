package models;

import java.util.List;
import java.util.Objects;

public class NodeModel
{

    private String id;

    private String name;

    private String nodeType;

    private boolean isFolder;

    private boolean isFile;

    private boolean isLocked;

    private String modifiedAt;

    private Object modifiedByUser;

    private String createdAt;

    private String archivedAt;

    private Object archivedByUser;

    private Object createdByUser;

    private String parentId;

    private boolean isLink;

    private Object content;

    private List<String> aspectNames;

    private Object properties;

    private List<String> allowableOperations;

    private Object path;

    private Object permissions;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNodeType()
    {
        return nodeType;
    }

    public void setNodeType(String nodeType)
    {
        this.nodeType = nodeType;
    }

    public boolean isIsFolder()
    {
        return isFolder;
    }

    public void setIsFolder(boolean folder)
    {
        isFolder = folder;
    }

    public boolean isIsFile()
    {
        return isFile;
    }

    public void setIsFile(boolean file)
    {
        isFile = file;
    }

    public boolean isIsLocked()
    {
        return isLocked;
    }

    public void setIsLocked(boolean locked)
    {
        isLocked = locked;
    }

    public String getModifiedAt()
    {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt)
    {
        this.modifiedAt = modifiedAt;
    }

    public Object getModifiedByUser()
    {
        return modifiedByUser;
    }

    public void setModifiedByUser(Object modifiedByUser)
    {
        this.modifiedByUser = modifiedByUser;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getArchivedAt()
    {
        return archivedAt;
    }

    public void setArchivedAt(String archivedAt)
    {
        this.archivedAt = archivedAt;
    }

    public Object getArchivedByUser()
    {
        return archivedByUser;
    }

    public void setArchivedByUser(Object archivedByUser)
    {
        this.archivedByUser = archivedByUser;
    }

    public Object getCreatedByUser()
    {
        return createdByUser;
    }

    public void setCreatedByUser(Object createdByUser)
    {
        this.createdByUser = createdByUser;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public boolean isLink()
    {
        return isLink;
    }

    public void setLink(boolean link)
    {
        isLink = link;
    }

    public Object getContent()
    {
        return content;
    }

    public void setContent(Object content)
    {
        this.content = content;
    }

    public List<String> getAspectNames()
    {
        return aspectNames;
    }

    public void setAspectNames(List<String> aspectNames)
    {
        this.aspectNames = aspectNames;
    }

    public Object getProperties()
    {
        return properties;
    }

    public void setProperties(Object properties)
    {
        this.properties = properties;
    }

    public List<String> getAllowableOperations()
    {
        return allowableOperations;
    }

    public void setAllowableOperations(List<String> allowableOperations)
    {
        this.allowableOperations = allowableOperations;
    }

    public Object getPath()
    {
        return path;
    }

    public void setPath(Object path)
    {
        this.path = path;
    }

    public Object getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Object permissions)
    {
        this.permissions = permissions;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NodeModel nodeModel = (NodeModel) o;
        return id.equals(nodeModel.id) && name.equals(nodeModel.name);
    }

    @Override public int hashCode()
    {
        return Objects.hash(id, name);
    }
}
