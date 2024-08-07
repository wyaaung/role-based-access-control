package com.wyaaung.rbac.controller;

import com.wyaaung.rbac.dto.MessageDto;
import com.wyaaung.rbac.exception.DuplicatePermissionException;
import com.wyaaung.rbac.exception.DuplicateRoleException;
import com.wyaaung.rbac.exception.PermissionNotFoundException;
import com.wyaaung.rbac.exception.RoleNotFoundException;
import com.wyaaung.rbac.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(BAD_REQUEST)
  public final MessageDto handleValidationException(final ValidationException validationException) {
    LOGGER.error(validationException.getMessage(), validationException);
    return new MessageDto(validationException.getMessage());
  }

  @ExceptionHandler(PermissionNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public final MessageDto handlePermissionNotFoundException(final PermissionNotFoundException permissionNotFoundException) {
    LOGGER.warn(permissionNotFoundException.getMessage(), permissionNotFoundException);
    return new MessageDto(permissionNotFoundException.getMessage());
  }

  @ExceptionHandler(DuplicatePermissionException.class)
  @ResponseStatus(CONFLICT)
  public final MessageDto handlePermissionAlreadyExistException(final DuplicatePermissionException duplicatePermissionException) {
    LOGGER.error(duplicatePermissionException.getMessage(), duplicatePermissionException);
    return new MessageDto(duplicatePermissionException.getMessage());
  }

  @ExceptionHandler(RoleNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public final MessageDto handleRuleNotFoundException(final RoleNotFoundException roleNotFoundException) {
    LOGGER.warn(roleNotFoundException.getMessage(), roleNotFoundException);
    return new MessageDto(roleNotFoundException.getMessage());
  }

  @ExceptionHandler(DuplicateRoleException.class)
  @ResponseStatus(CONFLICT)
  public final MessageDto handleDuplicateRoleException(final DuplicateRoleException duplicateRoleException) {
    LOGGER.error(duplicateRoleException.getMessage(), duplicateRoleException);
    return new MessageDto(duplicateRoleException.getMessage());
  }
}
