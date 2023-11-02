package kr.kernel.teachme.member;

/**
 * 이미 등록된 유저를 재등록하려고 할때 발생하는 Exception
 */
public class AlreadyRegisteredMemberException extends RuntimeException {

    public AlreadyRegisteredMemberException(String message) {
        super(message);
    }

    public AlreadyRegisteredMemberException() {
        super("이미 등록된 유저입니다.");
    }
}
