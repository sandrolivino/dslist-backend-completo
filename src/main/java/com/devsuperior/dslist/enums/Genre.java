package com.devsuperior.dslist.enums;

public enum Genre {
    RPG(1),
    ACTION(2),
    ACTION_ADVENTURE(3),
    PLATFORM(4),
    PUZZLE(5),
    SIMMULATION(6),
    STRATEGY(7),
    SPORT(8);

    private int code;

    private Genre(int code) {this.code = code;}

    public int getCode() {
        return code;
    }

    // Lembrar de alterar a propriedade genre de Genre para Integer, na classe Game
    public static Genre valueOf(int code) {
        for (Genre genre : Genre.values()) {
            if (code == genre.getCode()) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
