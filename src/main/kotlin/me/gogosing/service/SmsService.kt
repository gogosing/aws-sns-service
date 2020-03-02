package me.gogosing.service

import me.gogosing.model.PhoneNumber
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sns.SnsAsyncClient
import software.amazon.awssdk.services.sns.model.MessageAttributeValue
import software.amazon.awssdk.services.sns.model.PublishRequest

/**
 * Created by JinBum Jeong on 2020/03/02.
 */
interface SmsService {

    /**
     * sms message 전송.
     * @param mobile 수신자 정보.
     * @param message 전송할 message.
     */
    fun sendMessage(phoneNumber: PhoneNumber, message: String)
}

@Service
class AwsSnsServiceImpl(
    private val snsAsyncClient: SnsAsyncClient
) : SmsService {

    companion object {
        private val logger = LoggerFactory.getLogger(AwsSnsServiceImpl::class.java)
    }

    /**
     * 문자 메시지 속성 설정.
     * @see <a href="https://docs.aws.amazon.com/ko_kr/sns/latest/dg/sms_publish-to-phone.html#sms_publish_sdk"
     *      target="_top">sms_publish_sdk</a>
     */
    private val defaultMessageAttributes = mutableMapOf<String, MessageAttributeValue>(
        "AWS.SNS.SMS.SMSType" to MessageAttributeValue.builder()
            .stringValue("Promotional")
            .dataType("String").build()
    )

    override fun sendMessage(phoneNumber: PhoneNumber, message: String) {
        val phoneNumber = "${phoneNumber.country}${phoneNumber.numbers}"
        try {
            snsAsyncClient.publish(
                PublishRequest.builder()
                    .message(message)
                    .phoneNumber(phoneNumber)
                    .messageAttributes(defaultMessageAttributes)
                    .build()
            )
        } catch (exception: UnsupportedOperationException) {
            logger.error("SMS 메시지 전송 중 오류가 발생하였습니다.", exception)
        }
    }
}
