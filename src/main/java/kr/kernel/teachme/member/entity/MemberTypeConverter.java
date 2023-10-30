package kr.kernel.teachme.member.entity;

import javax.persistence.AttributeConverter;

public class MemberTypeConverter implements AttributeConverter<MemberType, String> {

    @Override
    public String convertToDatabaseColumn(MemberType memberType) { return memberType.getType(); }

    @Override
    public MemberType convertToEntityAttribute(String type) { return MemberType.ofType(type); }
}
