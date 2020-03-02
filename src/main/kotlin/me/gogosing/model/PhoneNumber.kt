package me.gogosing.model

/**
 * 전화번호 모델.
 */
data class PhoneNumber(

    /**
     * 전화 국가번호.
     */
    val country: String,

    /**
     * 국가번호를 제외한 전화번호.
     */
    val numbers: String
)
