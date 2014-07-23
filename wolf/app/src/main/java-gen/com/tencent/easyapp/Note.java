package com.tencent.easyapp;

import com.tencent.easyapp.DaoSession;
import com.tencent.easyapp.ui.common.SampleBaseActivity;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table NOTE.
 */
public class Note {

    private Long id;
    /** Not-null value. */
    private String title;
    /** Not-null value. */
    private String content;
    private long create_timestamp;
    private long update_timestamp;
    private long resource_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient NoteDao myDao;

    private Resource resource;
    private Long resource__resolvedKey;


    public Note() {
    }

    public Note(Long id) {
        this.id = id;
    }

    public Note(Long id, String title, String content, long create_timestamp, long update_timestamp, long resource_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_timestamp = create_timestamp;
        this.update_timestamp = update_timestamp;
        this.resource_id = resource_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Not-null value. */
    public String getContent() {
        return content;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(long create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public long getUpdate_timestamp() {
        return update_timestamp;
    }

    public void setUpdate_timestamp(long update_timestamp) {
        this.update_timestamp = update_timestamp;
    }

    public long getResource_id() {
        return resource_id;
    }

    public void setResource_id(long resource_id) {
        this.resource_id = resource_id;
    }

    /** To-one relationship, resolved on first access. */
    public Resource getResource() {
        long __key = this.resource_id;
        if (resource__resolvedKey == null || !resource__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                daoSession = SampleBaseActivity.getDaoSession();
//                throw new DaoException("Entity is detached from DAO context");
            }
            ResourceDao targetDao = daoSession.getResourceDao();
            Resource resourceNew = targetDao.load(__key);
            synchronized (this) {
                resource = resourceNew;
            	resource__resolvedKey = __key;
            }
        }
        return resource;
    }

    public Resource getSimpleResource(){
        return  resource;
    }

    public void setResource(Resource resource) {
        if (resource == null) {
            throw new DaoException("To-one property 'resource_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.resource = resource;
            resource_id = resource.getId();
            resource__resolvedKey = resource_id;
        }
        this.resource = resource;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
