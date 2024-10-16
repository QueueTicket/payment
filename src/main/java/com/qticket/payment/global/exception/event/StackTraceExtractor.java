package com.qticket.payment.global.exception.event;

import java.util.Arrays;

public sealed class StackTraceExtractor permits ThrowsExceptionEvent {

    private static final int IMPLICATED_STACK_TRACE_LINE_COUNT = 20;
    private static final String PRETTY_PRINT_FORMAT = "\n\t";
    private final StackTraceElement[] stackTraceElements;

    protected StackTraceExtractor(Exception exception) {
        this.stackTraceElements = exception.getStackTrace();
    }

    public String formatStackTrace() {
        StringBuilder stringBuilder = new StringBuilder();

        appendAbbreviatedStackTrace(stringBuilder, stackTraceElements);
        return stringBuilder.toString();
    }

    private void appendAbbreviatedStackTrace(StringBuilder stringBuilder,
        StackTraceElement[] elements) {
        Arrays.stream(elements)
            .limit(IMPLICATED_STACK_TRACE_LINE_COUNT)
            .forEach(element -> {
                stringBuilder.append(PRETTY_PRINT_FORMAT);
                stringBuilder.append(element);
                appendNewLineIfLastElement(stringBuilder, element, elements);
            });
    }

    private void appendNewLineIfLastElement(
        StringBuilder stringBuilder,
        StackTraceElement element,
        StackTraceElement[] elements
    ) {
        if (isLastElement(element, elements)) {
            stringBuilder.append("\n");
        }
    }

    private boolean isLastElement(StackTraceElement element, StackTraceElement[] elements) {
        return Arrays.asList(elements).indexOf(element) == IMPLICATED_STACK_TRACE_LINE_COUNT - 1;
    }

}
