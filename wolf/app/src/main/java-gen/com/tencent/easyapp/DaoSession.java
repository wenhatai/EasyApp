package com.tencent.easyapp;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.tencent.easyapp.Resource;
import com.tencent.easyapp.Note;

import com.tencent.easyapp.ResourceDao;
import com.tencent.easyapp.NoteDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig resourceDaoConfig;
    private final DaoConfig noteDaoConfig;

    private final ResourceDao resourceDao;
    private final NoteDao noteDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        resourceDaoConfig = daoConfigMap.get(ResourceDao.class).clone();
        resourceDaoConfig.initIdentityScope(type);

        noteDaoConfig = daoConfigMap.get(NoteDao.class).clone();
        noteDaoConfig.initIdentityScope(type);

        resourceDao = new ResourceDao(resourceDaoConfig, this);
        noteDao = new NoteDao(noteDaoConfig, this);

        registerDao(Resource.class, resourceDao);
        registerDao(Note.class, noteDao);
    }
    
    public void clear() {
        resourceDaoConfig.getIdentityScope().clear();
        noteDaoConfig.getIdentityScope().clear();
    }

    public ResourceDao getResourceDao() {
        return resourceDao;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

}