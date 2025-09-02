package com.womantech.mowo.domain.policy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegionCode {
    // 서울특별시 구
    GANGNAM("1168000000", "강남구"),
    GANGDONG("1174000000", "강동구"),
    GANGBUK("1130500000", "강북구"),
    GANGSEO("1150000000", "강서구"),
    GWANAK("1162000000", "관악구"),
    GWANGJIN("1121500000", "광진구"),
    GURO("1153000000", "구로구"),
    GEUMCHEON("1154500000", "금천구"),
    NOWON("1135000000", "노원구"),
    DOBONG("1132000000", "도봉구"),
    DONGDAEMUN("1123000000", "동대문구"),
    DONGJAK("1159000000", "동작구"),
    MAPO("1144000000", "마포구"),
    SEODAEMUN("1141000000", "서대문구"),
    SEOCHO("1165000000", "서초구"),
    SEONGDONG("1120000000", "성동구"),
    SEONGBUK("1129000000", "성북구"),
    SONGPA("1171000000", "송파구"),
    YANGCHEON("1147000000", "양천구"),
    YEONGDEUNGPO("1156000000", "영등포구"),
    YONGSAN("1117000000", "용산구"),
    EUNPYEONG("1138000000", "은평구"),
    JONGNO("1111000000", "종로구"),
    JUNG("1114000000", "중구"),
    JUNGNANG("1126000000", "중랑구");
    
    private final String code;
    private final String name;
    
    public static RegionCode fromCode(String code) {
        for (RegionCode regionCode : values()) {
            if (regionCode.getCode().equals(code)) {
                return regionCode;
            }
        }
        throw new IllegalArgumentException("Unknown region code: " + code);
    }
    
    public static RegionCode fromName(String name) {
        for (RegionCode regionCode : values()) {
            if (regionCode.getName().equals(name)) {
                return regionCode;
            }
        }
        throw new IllegalArgumentException("Unknown region name: " + name);
    }
}
