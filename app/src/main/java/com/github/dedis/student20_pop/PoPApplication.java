package com.github.dedis.student20_pop;

import android.app.Application;
import android.content.Context;

import androidx.annotation.VisibleForTesting;

import com.github.dedis.student20_pop.model.Lao;
import com.github.dedis.student20_pop.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PoPApplication extends Application {

    private static Context appContext;

    private Person person;
    private List<Lao> laos;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        if(person == null) {
            // TODO: when can the user change/choose its name
            setPerson(new Person("USER"));
        }
        if(laos == null) {
            setLaos(new ArrayList<>());
        }
    }

    /**
     *
     * @return PoP Application Context
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     *
     * @return Person corresponding to the user
     */
    public Person getPerson() {
        return person;
    }

    /**
     *
     * @return list of LAOs corresponding to the user
     */
    public List<Lao> getLaos() {
        return laos;
    }

    public void setPerson(Person person) {
        if(person != null) {
            this.person = person;
        }
    }

    public void setLaos(List<Lao> laos) {
        if(laos != null) {
            this.laos = laos;
        }
    }
}