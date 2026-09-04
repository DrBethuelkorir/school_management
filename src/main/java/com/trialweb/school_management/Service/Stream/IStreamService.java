package com.trialweb.school_management.Service.Stream;


import com.trialweb.school_management.Dtos.StreamDto;
import com.trialweb.school_management.Responses.StreamResponse;

public interface IStreamService {
    StreamResponse createStream(StreamDto streamDto);
    StreamResponse updateStream(Long id, StreamDto streamDto);
    StreamResponse getStreamById(Long id);
    StreamResponse getAllStreams();
    StreamResponse deleteStream(Long id);
    StreamResponse getStreamsByClass(Long classId);
    StreamResponse addStudentToStream(Long streamId, Long studentId);
    StreamResponse removeStudentFromStream(Long streamId, Long studentId);
}