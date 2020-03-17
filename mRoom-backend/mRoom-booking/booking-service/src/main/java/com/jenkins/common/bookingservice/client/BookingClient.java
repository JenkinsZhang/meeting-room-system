package com.jenkins.common.bookingservice.client;

import com.jenkins.common.roomInterface.api.RoomApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author JenkinsZhang
 * @date 2020/3/14
 */
@Component
@FeignClient(name = "roomInfo-service")
public interface BookingClient extends RoomApi {
}
