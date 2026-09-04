package com.trialweb.school_management.Service.Stream;

import com.trialweb.school_management.Dtos.StreamDto;
import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Models.Stream;
import com.trialweb.school_management.Models.Students;
import com.trialweb.school_management.Models.Teachers;
import com.trialweb.school_management.Repositories.ClassesRepository;
import com.trialweb.school_management.Repositories.StreamRepository;
import com.trialweb.school_management.Repositories.StudentsRepository;
import com.trialweb.school_management.Repositories.TeachersRepository;
import com.trialweb.school_management.Responses.StreamResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreamService implements IStreamService {

    // ==================== DEPENDENCY INJECTIONS ====================

    private final   StreamRepository streamRepository;
    private final ClassesRepository classesRepository;
    private  final TeachersRepository teachersRepository;
    private final StudentsRepository studentsRepository;

    // ==================== CREATE OPERATIONS ====================

    @Override
    public StreamResponse createStream(StreamDto streamDto) {
        StreamResponse response = new StreamResponse();

        try {
            // Validate if stream name already exists
            if (streamRepository.existsByName(streamDto.getName())) {
                throw new RuntimeException("Stream name already exists: " + streamDto.getName());
            }

            // Build stream entity
            Stream stream = buildStreamEntity(streamDto);

            // Save and map response
            Stream savedStream = streamRepository.save(stream);
            StreamDto savedStreamDto = Utils.mapStreamEntityToStreamDto(savedStream);

            response.setStatusCode(201);
            response.setMessage("Stream created successfully");
            response.setStreamDto(savedStreamDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating stream: " + e.getMessage());
        }

        return response;
    }

    // ==================== READ OPERATIONS ====================

    @Override
    public StreamResponse getStreamById(Long id) {
        StreamResponse response = new StreamResponse();

        try {
            Stream stream = findStreamOrThrow(id);
            StreamDto streamDto = Utils.mapStreamEntityToStreamDto(stream);

            response.setStatusCode(200);
            response.setMessage("Stream retrieved successfully");
            response.setStreamDto(streamDto);

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving stream: " + e.getMessage());
        }

        return response;
    }

    @Override
    public StreamResponse getAllStreams() {
        StreamResponse response = new StreamResponse();

        try {
            List<Stream> streams = streamRepository.findAll();
            List<StreamDto> streamDtos = streams.stream()
                    .map(Utils::mapStreamEntityToStreamDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Streams retrieved successfully");
            response.setStreamsDto(streamDtos);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving streams: " + e.getMessage());
        }

        return response;
    }

    @Override
    public StreamResponse getStreamsByClass(Long classId) {
        StreamResponse response = new StreamResponse();

        try {
            Classes classes = findClassOrThrow(classId);
            List<Stream> streams = streamRepository.findByClassesId(classId);
            List<StreamDto> streamDtos = streams.stream()
                    .map(Utils::mapStreamEntityToStreamDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Streams for class '" + classes.getName() + "' retrieved successfully");
            response.setStreamsDto(streamDtos);

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving streams: " + e.getMessage());
        }

        return response;
    }

    // ==================== UPDATE OPERATIONS ====================

    @Override
    public StreamResponse updateStream(Long id, StreamDto streamDto) {
        StreamResponse response = new StreamResponse();

        try {
            Stream stream = findStreamOrThrow(id);

            // Update fields
            updateStreamFields(stream, streamDto);

            // Save and map response
            Stream updatedStream = streamRepository.save(stream);
            StreamDto updatedStreamDto = Utils.mapStreamEntityToStreamDto(updatedStream);

            response.setStatusCode(200);
            response.setMessage("Stream updated successfully");
            response.setStreamDto(updatedStreamDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating stream: " + e.getMessage());
        }

        return response;
    }

    // ==================== DELETE OPERATIONS ====================

    @Override
    public StreamResponse deleteStream(Long id) {
        StreamResponse response = new StreamResponse();

        try {
            Stream stream = findStreamOrThrow(id);

            // Check if stream has students
            if (!stream.getStudents().isEmpty()) {
                throw new RuntimeException("Cannot delete stream with assigned students. Remove students first.");
            }

            streamRepository.delete(stream);

            response.setStatusCode(200);
            response.setMessage("Stream deleted successfully");

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting stream: " + e.getMessage());
        }

        return response;
    }

    // ==================== STUDENT MANAGEMENT OPERATIONS ====================

    @Override
    @Transactional
    public StreamResponse addStudentToStream(Long streamId, Long studentId) {
        StreamResponse response = new StreamResponse();

        try {
            Stream stream = findStreamOrThrow(streamId);
            Students student = findStudentOrThrow(studentId);

            // Validate and handle student assignment
            validateAndAssignStudentToStream(stream, student);

            // Save changes
            streamRepository.save(stream);
            studentsRepository.save(student);

            StreamDto updatedStreamDto = Utils.mapStreamEntityToStreamDto(stream);

            response.setStatusCode(200);
            response.setMessage("Student added to stream successfully");
            response.setStreamDto(updatedStreamDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding student to stream: " + e.getMessage());
        }

        return response;
    }

    @Override
    @Transactional
    public StreamResponse removeStudentFromStream(Long streamId, Long studentId) {
        StreamResponse response = new StreamResponse();

        try {
            Stream stream = findStreamOrThrow(streamId);
            Students student = findStudentOrThrow(studentId);

            // Validate and remove student from stream
            validateAndRemoveStudentFromStream(stream, student);

            // Save changes
            streamRepository.save(stream);
            studentsRepository.save(student);

            StreamDto updatedStreamDto = Utils.mapStreamEntityToStreamDto(stream);

            response.setStatusCode(200);
            response.setMessage("Student removed from stream successfully");
            response.setStreamDto(updatedStreamDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error removing student from stream: " + e.getMessage());
        }

        return response;
    }

    // ==================== PRIVATE HELPER METHODS ====================

    /**
     * Builds a Stream entity from DTO
     */
    private Stream buildStreamEntity(StreamDto streamDto) {
        Stream stream = new Stream();
        stream.setName(streamDto.getName());
        stream.setTotalNumberOfStudents(0);

        if (streamDto.getClassesDto() != null && streamDto.getClassesDto().getId() != null) {
            Classes classes = findClassOrThrow(streamDto.getClassesDto().getId());
            stream.setClasses(classes);
        }

        if (streamDto.getTeachers() != null && streamDto.getTeachers().getId() != null) {
            Teachers teacher = findTeacherOrThrow(streamDto.getTeachers().getId());
            stream.setTeachers(teacher);
        }

        return stream;
    }

    /**
     * Updates Stream fields from DTO
     */
    private void updateStreamFields(Stream stream, StreamDto streamDto) {
        // Update name if provided
        if (streamDto.getName() != null && !streamDto.getName().isEmpty()) {
            validateUniqueStreamName(streamDto.getName(), stream.getId());
            stream.setName(streamDto.getName());
        }

        // Update class if provided
        if (streamDto.getClassesDto() != null && streamDto.getClassesDto().getId() != null) {
            Classes classes = findClassOrThrow(streamDto.getClassesDto().getId());
            stream.setClasses(classes);
        }

        // Update teacher if provided
        if (streamDto.getTeachers() != null && streamDto.getTeachers().getId() != null) {
            Teachers teacher = findTeacherOrThrow(streamDto.getTeachers().getId());
            stream.setTeachers(teacher);
        }
    }

    /**
     * Validates stream name uniqueness
     */
    private void validateUniqueStreamName(String name, Long currentStreamId) {
        streamRepository.findByName(name).ifPresent(existingStream -> {
            if (!existingStream.getId().equals(currentStreamId)) {
                throw new RuntimeException("Stream name already exists: " + name);
            }
        });
    }

    /**
     * Validates and assigns student to stream
     */
    private void validateAndAssignStudentToStream(Stream stream, Students student) {
        // Check if student is already in this stream
        if (student.getStream() != null && student.getStream().getId().equals(stream.getId())) {
            throw new RuntimeException("Student is already assigned to this stream");
        }

        // Remove student from current stream if any
        if (student.getStream() != null) {
            Stream currentStream = student.getStream();
            currentStream.getStudents().remove(student);
            currentStream.setTotalNumberOfStudents(currentStream.getStudents().size());
            streamRepository.save(currentStream);
        }

        // Add student to new stream
        stream.getStudents().add(student);
        stream.setTotalNumberOfStudents(stream.getStudents().size());
        student.setStream(stream);
    }

    /**
     * Validates and removes student from stream
     */
    private void validateAndRemoveStudentFromStream(Stream stream, Students student) {
        // Check if student is in this stream
        if (student.getStream() == null || !student.getStream().getId().equals(stream.getId())) {
            throw new RuntimeException("Student is not assigned to this stream");
        }

        // Remove student from stream
        stream.getStudents().remove(student);
        stream.setTotalNumberOfStudents(stream.getStudents().size());
        student.setStream(null);
    }

    // ==================== FINDER METHODS ====================

    private Stream findStreamOrThrow(Long id) {
        return streamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found with id: " + id));
    }

    private Classes findClassOrThrow(Long id) {
        return classesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
    }

    private Teachers findTeacherOrThrow(Long id) {
        return teachersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    private Students findStudentOrThrow(Long id) {
        return studentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }
}