package com.abelm.blog_api.service.exceptions;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

@Getter
public class NotFoundAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String errorKey;

    public NotFoundAlertException(String errorKey, String title) {
        super(null, title, Status.NOT_FOUND, null);
        this.errorKey = errorKey;
    }

    public NotFoundAlertException(String errorKey, String title, String detail) {
        super(null, title, Status.NOT_FOUND, detail);
        this.errorKey = errorKey;
    }
}
