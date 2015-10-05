package br.org.tutty.collaborative_whiteboard.cw_web.validation;

import br.org.tutty.collaborative_whiteboard.cw_web.utils.FacesMessageUtil;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by drferreira on 27/04/15.
 */
@FacesValidator("custom.emailValidator")
public class EmailValidation implements Validator{
    private Pattern pattern;
    @Inject
    private FacesMessageUtil facesMessageUtil;

    private static String MESSAGE_KEY = "email.validation.error";
    private static String MESSAGE_KEY_DETAIL = "email.validation.error.detail";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        pattern = this.pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(value.toString());

        if(!matcher.matches()){
            try {
                throw new ValidatorException(facesMessageUtil.getFacesMessage(FacesMessage.SEVERITY_ERROR, MESSAGE_KEY, MESSAGE_KEY_DETAIL));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
