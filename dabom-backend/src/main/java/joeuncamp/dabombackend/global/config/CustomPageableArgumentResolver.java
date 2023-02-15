package joeuncamp.dabombackend.global.config;

import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CustomPageableArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private static final int MINIMUM_PAGE = 0;
    private static final int MINIMUM_SIZE = 1;
    private static final int MAXIMUM_SIZE = 50;

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String page = webRequest.getParameter("page");
        final String size = webRequest.getParameter("size");

        if (isInvalidPageAndSize(page, size)) {
            throw new CIllegalArgumentException("페이지 요청이 잘못되었습니다.");
        }

        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }

    private boolean isInvalidPageAndSize(final String page, final String size) {
        if (page == null && size == null) {
            return false;
        }

        if (page == null || size == null) {
            return true;
        }

        return isInvalidPage(page) || isInvalidSize(size);
    }

    private boolean isInvalidPage(final String page) {
        return !isNumeric(page) || (Integer.parseInt(page) < MINIMUM_PAGE) ;
    }

    private boolean isInvalidSize(final String size) {
        return !isNumeric(size) || (Integer.parseInt(size) < MINIMUM_SIZE) || (Integer.parseInt(size) > MAXIMUM_SIZE);
    }

    private boolean isNumeric(final String text) {
        if (text.isBlank()) {
            return false;
        }

        for (char character : text.toCharArray()) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }

        return true;
    }
}
