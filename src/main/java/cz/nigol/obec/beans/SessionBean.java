package cz.nigol.obec.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.beans.admin.SettingsBean;
import cz.nigol.obec.dev.PrepareDevData;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.SettingsService;

@Named
@SessionScoped
public class SessionBean implements Serializable {
  private static final long serialVersionUID = 9212583897602369531L;
  @Inject
  private FacesContext facesContext;
  @Inject
  private PrepareDevData prepareDevData;
  @Inject
  private SettingsService settingsService;
  private User user;
  private Settings settings;
  private String pathAfterLogin = "/uzivatel/prehled.jsf";
  private boolean pollPerformed = false;
  private String queryString = "";

  @PostConstruct
  public void init() {
    // prepareDevData.createData();
    settings = settingsService.findById(SettingsBean.ID);
  }

  public boolean pathAllowed(String path) {
    Path pt = new Path();
    pt.setId(path);
    return user.getRole().getPaths().contains(pt);
  }

  public boolean pathsAllowed(String... paths) {
    boolean result = false;
    for (String path : paths) {
      result = result || pathAllowed(path);
    }
    return result;
  }

  public String logout() {
    user = null;
    facesContext.getExternalContext().invalidateSession();
    return "/index.xhtml?faces-redirect=true";
  }

  public String getQueryString() {
    return queryString;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * @return the settings
   */
  public Settings getSettings() {
    return settings;
  }

  /**
   * @param settings the settings to set
   */
  public void setSettings(Settings settings) {
    this.settings = settings;
  }

  /**
   * @return the pathAfterLogin
   */
  public String getPathAfterLogin() {
    return pathAfterLogin;
  }

  /**
   * @param pathAfterLogin the pathAfterLogin to set
   */
  public void setPathAfterLogin(String pathAfterLogin) {
    this.pathAfterLogin = pathAfterLogin;
  }

  public boolean getPollPerformed() {
    return pollPerformed;
  }

  public void setPollPerformed(boolean pollPerformed) {
    this.pollPerformed = pollPerformed;
  }
}
