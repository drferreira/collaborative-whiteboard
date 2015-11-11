package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.UserEdition;
import cw.dtos.LoggedUser;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by drferreira on 28/08/15.
 */
@Named
@ViewScoped
public class ProfileController extends GenericController implements Serializable {
	private static String INVALID_TYPE_KEY = "invalid.type.picture";
	private static String INVALID_TYPE_KEY_DETAIL = "invalid.type.picture.detail";
	private static String INVALID_SIZE_KEY_DETAIL = "invalid.size.picture.detail";
	private static String SAVE_KEY = "profile.edition.save";
	private static String SAVE_KEY_DETAIL = "profile.edition.save.detail";
	private static String TYPE_FILE_JPG = "jpeg";
	private static String TYPE_FILE_PNG = "png";

	@Inject
	private SessionContext sessionContext;

	@Inject
	private UserEdition userEdition;

	@Inject
	private UserService userService;

	@PostConstruct
	public void setUp(){
        try {
            LoggedUser loggedUser = sessionContext.getLoggedUser();
            userEdition.init(loggedUser.getUser());

        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

	public void save(){
		User changedUser = userEdition.toEntity();
		userService.update(changedUser);
		facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO,SAVE_KEY,SAVE_KEY_DETAIL);
	}

	public UserEdition getUserEdition() {
		return userEdition;
	}

	public void setUserEdition(UserEdition userEdition) {
		this.userEdition = userEdition;
	}

	public void updatePicture(FileUploadEvent fileUploadEvent) {
		UploadedFile file = fileUploadEvent.getFile();

		if (isValidPictureType(file) && isValidPictureSize(file)) {
			userEdition.getProfilePicture().setProfilePicture(file);

		} else {
		}
	}

	private Boolean isValidPictureType(UploadedFile uploadedFile) {
		String contentType = uploadedFile.getContentType();
		Boolean result = contentType.contains(TYPE_FILE_JPG) || contentType.contains(TYPE_FILE_PNG);

		if(!result){
			facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, INVALID_TYPE_KEY, INVALID_TYPE_KEY_DETAIL);
		}

		return result;
	}

	private Boolean isValidPictureSize(UploadedFile uploadedFile){
		Boolean result = uploadedFile.getSize() < 300000 ? Boolean.TRUE : Boolean.FALSE;

		if(!result){
			facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, INVALID_TYPE_KEY, INVALID_SIZE_KEY_DETAIL);
		}

		return result;

	}
}
