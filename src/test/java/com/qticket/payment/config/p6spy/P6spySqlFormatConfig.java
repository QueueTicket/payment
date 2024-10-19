package com.qticket.payment.config.p6spy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import java.sql.SQLException;
import java.util.Locale;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class P6spySqlFormatConfig extends JdbcEventListener implements MessageFormattingStrategy {

    public static final String CREATE = "create";
    public static final String ALTER = "alter";
    public static final String COMMENT = "comment";
    public static final String EXECUTE_QUERY_LOG_FORMAT = "\n[Connection : %s][%s][%d ms] %s";

    @Override
    public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(getClass().getName());
    }

    @Override
    public String formatMessage(
        int connectionId,
        String now,
        long duration,
        String category,
        String prepared,
        String sql,
        String url
    ) {
        return String.format(
            EXECUTE_QUERY_LOG_FORMAT,
            connectionId,
            category,
            duration,
            formatSql(category, sql)
        );
    }

    private String formatSql(String category, String sql) {
        if (isNotStatementCategory(category)) {
            return sql;
        }
        return formatSqlByStatementCategory(sql);
    }

    private boolean isNotStatementCategory(String category) {
        return !isStatementCategory(category);
    }

    private boolean isStatementCategory(String category) {
        return Category.STATEMENT.getName().equals(category);
    }

    private String formatSqlByStatementCategory(String sql) {
        if (isDataDefinitionLanguage(sql)) {
            return toFormat(FormatStyle.DDL, sql);
        }
        return applyHighlighter(toFormat(FormatStyle.BASIC, sql));
    }

    private boolean isDataDefinitionLanguage(String sql) {
        String statement = toLowerCase(sql);
        return statement.startsWith(CREATE) || statement.startsWith(ALTER) || statement.startsWith(
            COMMENT);
    }

    private String toLowerCase(String sql) {
        return sql.trim().toLowerCase(Locale.ROOT);
    }

    private String toFormat(FormatStyle formatStyle, String sql) {
        return getFormatter(formatStyle).format(sql);
    }

    private Formatter getFormatter(FormatStyle formatStyle) {
        return formatStyle.getFormatter();
    }

    private String applyHighlighter(String sql) {
        return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
    }

}
