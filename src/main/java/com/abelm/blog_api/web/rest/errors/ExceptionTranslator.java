package com.abelm.blog_api.web.rest.errors;


import com.abelm.blog_api.service.exceptions.BadRequestAlertException;
import com.abelm.blog_api.service.exceptions.NotFoundAlertException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@Slf4j
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {

    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String ERROR_KEY = "errorKey";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private static final String SUBMIT_TIME_KEY = "submitTime";

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public ResponseEntity<Problem> process(ResponseEntity<Problem> entity, NativeWebRequest request) {
        Problem problem = entity.getBody();
        if (problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem) {
            HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
            String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
            ProblemBuilder builder = Problem.builder()
                    .withStatus(problem.getStatus())
                    .withTitle(problem.getTitle())
                    .with(PATH_KEY, requestUri)
                    .with(SUBMIT_TIME_KEY, ZonedDateTime.now());

            if (problem instanceof ConstraintViolationProblem) {
                builder.with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations());
            } else {
                if (problem.getDetail() != null) {
                    builder.withDetail(problem.getDetail());
                }
                if (problem.getInstance() != null) {
                    builder.withInstance(problem.getInstance());
                }
                problem.getParameters().forEach(builder::with);
            }
            return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
        }
        return entity;
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVM> fieldErrors = result
                .getFieldErrors()
                .stream()
                .map(f -> new FieldErrorVM(
                        f.getObjectName().replaceFirst("DTO$", ""),
                        f.getField(),
                        StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode())
                ).collect(Collectors.toList());

        Problem problem = Problem
                .builder()
                .withStatus(Status.BAD_REQUEST)
                .withTitle("Validation errors")
                .withDetail("Multiple fields failed validation")
                .with(ERROR_KEY, "VALIDATION_ERRORS")
                .with(FIELD_ERRORS_KEY, fieldErrors)
                .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withTitle(ex.getTitle())
                .withDetail(ex.getDetail())
                .withStatus(ex.getStatus())
                .with(ERROR_KEY, ex.getErrorKey())
                .with(PATH_KEY, request.getNativeRequest(HttpServletRequest.class).getRequestURI())
                .with(SUBMIT_TIME_KEY, ZonedDateTime.now())
                .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleNotFoundAlertException(NotFoundAlertException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withTitle(ex.getTitle())
                .withDetail(ex.getDetail())
                .withStatus(ex.getStatus())
                .with(ERROR_KEY, ex.getErrorKey())
                .with(PATH_KEY, request.getNativeRequest(HttpServletRequest.class).getRequestURI())
                .with(SUBMIT_TIME_KEY, ZonedDateTime.now())
                .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withTitle("Concurrency Failure Exception")
                .withStatus(Status.CONFLICT)
                .build();
        return create(ex, problem, request);
    }
}


