package com.example.nodo.util;

import android.app.Application;
import android.database.CursorJoiner;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.nodo.data.NoDoDao;
import com.example.nodo.data.NoDoRoomDatabase;
import com.example.nodo.model.NoDo;

import java.util.List;

public class NoDoRepository {
    private NoDoDao noDoDao;
    private LiveData<List<NoDo>> allNoDos;

    public NoDoRepository(Application application) {
        NoDoRoomDatabase db = NoDoRoomDatabase.getDatabase(application);
        noDoDao = db.noDoDao();
        allNoDos = noDoDao.getAllItems();
    }
    public LiveData<List<NoDo>> getAllNoDos() {
        return allNoDos;
    }
    public void insert(NoDo noDo) {
        new insertAsyncTask(noDoDao).execute(noDo);
    }
    private class insertAsyncTask extends AsyncTask<NoDo, Void, Void> {
        private NoDoDao asyncTaskDao;
        public insertAsyncTask(NoDoDao dao) {
            asyncTaskDao = dao;

        }

        @Override
        protected Void doInBackground(NoDo... params) {
            //[obj1, obj2....]
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
