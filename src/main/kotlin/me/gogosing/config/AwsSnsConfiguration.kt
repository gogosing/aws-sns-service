package me.gogosing.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsAsyncClient

/**
 * Created by JinBum Jeong on 2020/03/02.
 */
@Configuration
class AwsSnsConfiguration(
    @Value("\${aws.access-key-id:}") val accessKeyId: String,
    @Value("\${aws.secret-access-key:}") val secretAccessKey: String
) {

    /**
     * AWS SNS Client 설정.
     * @see <a href="https://docs.aws.amazon.com/ko_kr/sns/latest/dg/sms_supported-countries.html"
     *      target="_top">AWS SNS Supported Regions</a>
     */
    @Bean
    fun snsAsyncClient(): SnsAsyncClient {
        return SnsAsyncClient.builder()
            .credentialsProvider { AwsBasicCredentials.create(accessKeyId, secretAccessKey) }
            .region(Region.US_WEST_2)
            .build()
    }
}
