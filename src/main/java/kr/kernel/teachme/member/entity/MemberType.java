package kr.kernel.teachme.member.entity;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum MemberType {

    CLIENT(1, "고객"), MANAGER(2, "관리자");

    private final int code;
    private final String type;

    private MemberType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static MemberType ofCode(int code) {
        return Arrays.stream(MemberType.values())
                .filter(e -> e.getCode() == code)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("코드에 해당하는 멤버 타입을 찾을 수 없습니다. 코드: " + code));
    }

    public static MemberType ofType(String type) {
        return Arrays.stream(MemberType.values())
                .filter(e -> e.getType().equals(type))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("타입에 해당하는 멤버 타입을 찾을 수 없습니다. 타입: " + type));
    }

    public int getCode() { return this.code; }

    public String getType() { return this.type; }
}
