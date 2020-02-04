package com.jenkins.common.bookingservice.mapper;

import com.jenkins.common.bookinginterface.model.BookingRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookingRecordMapper {

    public List<BookingRecord> selectRoomInformationById(@Param("room_id") int room_id);
}
