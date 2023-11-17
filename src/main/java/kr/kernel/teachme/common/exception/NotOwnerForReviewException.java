package kr.kernel.teachme.common.exception;

public class NotOwnerForReviewException extends RuntimeException {

    public NotOwnerForReviewException(String message) { super(message); }

    public NotOwnerForReviewException() { super("리뷰에 대한 권한이 없습니다."); }
}
