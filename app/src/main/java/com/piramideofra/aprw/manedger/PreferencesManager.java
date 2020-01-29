package com.piramideofra.aprw.manedger;


public interface PreferencesManager {

    String getURL();

    void setURL(String token);


    void setMyFirstTime(Boolean flag);

    Boolean getMyFirstTime();

    void setGameStart(Boolean flag);

    Boolean getGameStart();

    void setSateStartSte(Boolean flag);

    Boolean getSateStartSte();

}
