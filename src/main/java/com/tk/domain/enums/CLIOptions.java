package com.tk.domain.enums;

/**
 * CLIOptions
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public enum CLIOptions {
    ID("id"),
    ROLE("r"),
    PUB_KEY("pbk"),
    PRIV_KEY("prk"),
    REPUTATION("rep");

    private String option;

    CLIOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
