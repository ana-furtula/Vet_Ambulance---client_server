/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.validation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ANA
 */
public class Validator {

    private final List<String> validationErros;

    private Validator() {
        validationErros = new ArrayList();
    }

    public static Validator startValidation() {
        return new Validator();
    }

    public Validator validateNotNullOrEmpty(String value, String errorMessage) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            this.validationErros.add(errorMessage);
        }
        return this;
    }

    public Validator validateNotNull(Object value, String errorMessage) throws ValidationException {
        if (value == null) {
            this.validationErros.add(errorMessage);
        }
        return this;
    }

    public Validator validatePrice(BigDecimal value, String errorMessage) throws ValidationException {
        try {
            if (value == null || value.compareTo(BigDecimal.ZERO) == -1) {
                this.validationErros.add(errorMessage);
            }
        } catch (Exception ex) {
            this.validationErros.add(errorMessage);
        }
        return this;
    }
    
    public Validator validateQuantity(BigDecimal value, String errorMessage) throws ValidationException {
        try {
            if (value == null || value.compareTo(BigDecimal.ZERO) == -1) {
                this.validationErros.add(errorMessage);
            }
        } catch (Exception ex) {
            this.validationErros.add(errorMessage);
        }
        return this;
    }

    public Validator validateListIsNotEmpty(List list, String errorMessage) throws ValidationException {
        if (list == null || list.isEmpty()) {
            this.validationErros.add(errorMessage);
        }
        return this;
    }

    public void throwIfInvalide() throws ValidationException {
        if (!validationErros.isEmpty()) {
            throw new ValidationException(this.validationErros.stream().collect(Collectors.joining("\n")));
        }
    }
}
